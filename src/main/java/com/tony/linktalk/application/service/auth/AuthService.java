package com.tony.linktalk.application.service.auth;

import com.tony.linktalk.adapter.in.web.dto.response.auth.JwtResponseDto;
import com.tony.linktalk.adapter.in.web.dto.response.auth.MemberResponseDto;
import com.tony.linktalk.application.command.auth.SignInCommand;
import com.tony.linktalk.application.command.auth.SignOutCommand;
import com.tony.linktalk.application.command.auth.SignUpCommand;
import com.tony.linktalk.application.command.auth.token.ReIssueAccessTokenCommand;
import com.tony.linktalk.application.port.in.auth.AuthUseCase;
import com.tony.linktalk.application.port.out.member.CreateMemberPort;
import com.tony.linktalk.application.port.out.member.FindMemberPort;
import com.tony.linktalk.application.port.out.refreshtoken.CreateRefreshTokenPort;
import com.tony.linktalk.application.port.out.refreshtoken.DeleteRefreshTokenPort;
import com.tony.linktalk.application.port.out.refreshtoken.FindRefreshTokenPort;
import com.tony.linktalk.config.security.http.user.UserDetailsImpl;
import com.tony.linktalk.domain.Jwt;
import com.tony.linktalk.domain.Member;
import com.tony.linktalk.domain.RefreshToken;
import com.tony.linktalk.mapper.JwtMapper;
import com.tony.linktalk.mapper.MemberMapper;
import com.tony.linktalk.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 회원 인증 관련 비즈니스 로직을 처리하는 서비스 클래스
 * 회원 인증, JWT 관련 로직을 처리합니다.
 * Auth service는 member와
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService implements AuthUseCase {

    private final CreateMemberPort createMemberPort;
    private final CreateRefreshTokenPort createRefreshTokenPort;
    private final FindMemberPort findMemberPort;
    private final FindRefreshTokenPort findRefreshTokenPort;
    private final DeleteRefreshTokenPort deleteRefreshTokenPort;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final MemberMapper memberMapper;
    private final JwtMapper jwtMapper;

    @Value("${jwt.refreshTokenDurationMinutes}")
    private long refreshTokenDurationMinutes;


    /**
     * @param signInCommand 로그인 요청 도메인
     * @return JWT 토큰 발급 응답 DTO
     * @apiNote 로그인 요청을 처리하는 메서드
     * todo: 이 한가지 메서드가 가진 역할과 책임이 좀 많은것같은데 어떻게 할까 고민이다.
     */
    @Transactional
    @Override
    public JwtResponseDto signInAndPublishJwt(SignInCommand signInCommand) {
        // 1. 로그인 요청 도메인을 생성
        Member member = memberMapper.commandToDomain(signInCommand);

        // 2. authentication 객체를 생성하고 SecurityContext에 저장
        Authentication authentication = getAuthentication(member);

        // 3. JWT access 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        // 3. 유저 도메인에 이메일을 저장하고 DB에서 조회
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        member.changeEmail(email);
        Member findMember = findMemberPort.findMemberByEmail(email);

        // 4. JWT refresh 토큰을 생성하고 저장
        RefreshToken refreshToken = RefreshToken.of(findMember, refreshTokenDurationMinutes);
        RefreshToken savedRefreshToken = createRefreshTokenPort.createRefreshToken(refreshToken);

        // 5. JWT 도메인을 생성하고 조회해온 회원 도메인에 저장
        Jwt jwt = Jwt.of(accessToken, savedRefreshToken.getToken(), email, userDetails.getAuthorities());
        findMember.changeMemberInsideJwt(jwt);

        // 6. 활동 로그 저장 이벤트를 발행하고 JWT 토큰 발급 응답 DTO 반환
        return jwtMapper.domainToResponseDTO(jwt);
    }


    /**
     * @return 가입한 회원의 이메일
     * @Param signUpCommand 회원가입 요청 도메인
     * @apiNote 회원 생성 + (이벤트 발행)
     */
    @Transactional
    @Override
    public MemberResponseDto signUp(SignUpCommand signUpCommand) {
        // 1. 회원가입 요청 도메인을 생성
        Member member = memberMapper.commandToDomain(signUpCommand);

        // 2. 비밀번호 암호화
        member.changePassword(passwordEncoder.encode(member.getPassword()));

        // 3. 회원 저장
        Member savedMember = createMemberPort.createMember(member);

        // 4. DTO로 변환하여 반환
        return memberMapper.domainToResponseDTO(savedMember);
    }


    /**
     * @param signOutCommand 로그아웃 요청 도메인
     * @apiNote 로그아웃 + JWT 삭제 + 활동 로그 저장 event 발행
     */
    @Transactional
    @Override
    public Long signOut(SignOutCommand signOutCommand) {
        // 1. 로그아웃 요청 도메인을 생성
        Member member = memberMapper.commandToDomain(signOutCommand);

        // 2. 로그아웃 처리
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 2-1. SecurityContext에서 인증 정보를 가져와서 이메일을 추출
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String email = userDetails.getEmail();
            member.changeEmail(email);

            // 2-2. 회원 조회 후 RefreshToken 삭제
            Member findMember = findMemberPort.findMemberByEmail(member.getEmail());
            deleteRefreshTokenPort.deleteRefreshToken(findMember);

            // 2-3. SecurityContext에서 인증 정보 삭제
            SecurityContextHolder.clearContext();
        }

        // 3. 로그아웃한 회원 ID 반환 (예외가 없으면 로그아웃 성공이라 이 객체의 id를 반환)
        return member.getId();
    }


    /**
     * @param reIssueAccessTokenCommand JWT 토큰 갱신 요청 커맨드
     * @return 갱신된 JWT 토큰
     * @apiNote JWT 토큰 갱신 요청을 처리하는 메서드
     */
    @Transactional
    @Override
    public JwtResponseDto reIssueAccessToken(ReIssueAccessTokenCommand reIssueAccessTokenCommand) {
        // 1. refresh 토큰을 조회
        RefreshToken findRefreshToken = getRefreshToken(reIssueAccessTokenCommand);

        // 2. refresh 토큰의 유효성 검증 (회원 정보, 만료 날짜 존재여부, 만료 기간 확인)
        findRefreshToken.validRefreshToken();

        // 3. refresh 토큰의 유효성이 통과되었다면 새로운 access 토큰을 생성
        String newAccessToken = jwtTokenProvider.regenerateAccessToken();

        // 4. 갱신된 JWT 토큰 정보를 DTO에 담아 반환
        return JwtResponseDto.of(newAccessToken, findRefreshToken.getToken());
    }


    /**
     * @param member 로그인 요청 도메인
     * @return 생성된 Authentication 객체
     * @apiNote 로그인 요청을 처리하기 위해 Authentication 객체를 생성하고 SecurityContext에 저장하는 메서드
     */
    private Authentication getAuthentication(Member member) {
        // 1. 시큐리티를 활용하여 회원을 조회하고 인증 정보를 생성한다.
        Authentication authentication = createAuthenticationFrom(member);

        // 2. SecurityContext에 인증 정보를 저장한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. SecurityContext에 저장된 인증 정보를 반환한다.
        return authentication;
    }


    /**
     * @param member 로그인 요청 도메인
     * @return 생성된 Authentication 객체
     * @apiNote 로그인 요청을 처리하기 위해 Authentication 객체를 생성하는 메서드
     * authenticate 메서드가 호출되면 UserDetailsServiceImpl의 loadUserByUsername 메서드가 호출된다.
     * UserDetailsServiceImpl은 UserDetailsService 인터페이스를 구현한 클래스이다. (클래스를 검색해보자)
     * 즉, 여기서 DB에서 회원 정보를 가져와서 UserDetails 객체를 생성하고 반환한다. (클래스를 검색해보자)
     */
    private Authentication createAuthenticationFrom(Member member) {
        // 1. 회원 정보를 기반으로 인증 정보를 생성한다.
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword())
        );
    }


    /**
     * @param reIssueAccessTokenCommand JWT 토큰 갱신 요청 커맨드
     * @return 조회된 refresh 토큰
     * @apiNote refresh 토큰을 조회하는 메서드
     */
    private RefreshToken getRefreshToken(ReIssueAccessTokenCommand reIssueAccessTokenCommand) {
        RefreshToken refreshToken = RefreshToken.of(reIssueAccessTokenCommand.getRefreshToken());
        return findRefreshTokenPort.findRefreshToken(refreshToken);
    }

}
