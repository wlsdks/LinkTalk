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
import com.tony.linktalk.exception.ErrorCode;
import com.tony.linktalk.exception.LinkTalkException;
import com.tony.linktalk.mapper.JwtMapper;
import com.tony.linktalk.mapper.MemberMapper;
import com.tony.linktalk.util.JwtTokenProvider;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


/**
 * 회원 인증 관련 비즈니스 로직을 처리하는 서비스 클래스
 * 회원 인증, JWT 관련 로직을 처리합니다.
 * Auth service는 member와 jwt를 다루는 비즈니스 로직을 처리합니다.
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@UseCase
public class AuthService implements AuthUseCase {

    private final CreateMemberPort createMemberPort;
    private final CreateRefreshTokenPort createRefreshTokenPort;
    private final FindMemberPort findMemberPort;
    private final FindRefreshTokenPort findRefreshTokenPort;
    private final DeleteRefreshTokenPort deleteRefreshTokenPort;
    private final MemberMapper memberMapper;
    private final JwtMapper jwtMapper;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.refreshTokenDurationMinutes}")
    private long refreshTokenDurationMinutes;


    /**
     * @param signInCommand 로그인 요청 도메인
     * @return JWT 토큰 발급 응답 DTO
     * @apiNote 로그인 요청을 처리하는 메서드
     */
    @Transactional
    @Override
    public JwtResponseDto signInAndCreateJwt(SignInCommand signInCommand) {
        // 1. 로그인 요청 도메인을 생성
        Member member = memberMapper.commandToDomain(signInCommand);

        // 2. db에서 회원 조회
        Member findMember = findMemberPort.findMemberByEmail(member.getEmail());

        // 3. JWT access 토큰 생성
        String accessToken = jwtTokenProvider
                .generateAccessToken(findMember.getEmail(), findMember.getNickname(), findMember.getId());

        // 4. JWT refresh 토큰을 생성하고 저장
        RefreshToken refreshToken = RefreshToken.of(findMember, refreshTokenDurationMinutes);
        RefreshToken savedRefreshToken = createRefreshTokenPort.createRefreshToken(refreshToken);

        // 5. JWT 도메인을 생성하고 조회해온 회원 도메인에 저장
        Jwt jwt = Jwt.of(accessToken, savedRefreshToken.getToken(), findMember.getEmail());
        findMember.changeMemberInsideJwt(jwt);

        // 6. mapper를 통해 DTO로 변환하여 반환
        return jwtMapper.domainToResponseDTO(jwt);
    }


    /**
     * @param signUpCommand 회원가입 요청 도메인
     * @return 가입한 회원의 이메일
     * @apiNote 회원 생성
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
     * @apiNote 로그아웃 + JWT 삭제
     */
    @Transactional
    @Override
    public Long signOut(SignOutCommand signOutCommand) {
        // 1. 로그아웃 요청 도메인을 생성
        Member member = memberMapper.commandToDomain(signOutCommand);

        // 2. SecurityContext에서 인증 정보를 가져와서 이메일을 추출
        UserDetailsImpl userDetails = getUserDetails();

        // 3. 회원 정보를 검증한 후 RefreshToken 삭제 (검증 실패시 도메인 비즈니스에서 예외 처리)
        if (member.isSameEmail(userDetails.getEmail())) {
            // 회원 조회 후 RefreshToken 삭제
            Member findMember = findMemberPort.findMemberByEmail(member.getEmail());
            deleteRefreshTokenPort.deleteRefreshToken(findMember);

            // SecurityContext에서 인증 정보 삭제
            SecurityContextHolder.clearContext();
        }

        // 4. 로그아웃한 회원 ID 반환
        return member.getId();
    }


    /**
     * @param reIssueAccessTokenCommand JWT 토큰 갱신 요청 커맨드
     * @return 갱신된 JWT 토큰
     * @apiNote JWT 토큰 갱신 요청을 처리하는 메서드
     * 클라이언트가 서버에 요청을 보낼 때, 액세스 토큰이 만료된 경우 ExpiredJwtException이 발생합니다.
     * 이 경우, SecurityContextHolder에 인증 정보가 설정되지 않고, 클라이언트는 새로운 액세스 토큰을 발급받기 위해 API를 호출하면 이 메서드가 호출됩니다.
     */
    @Transactional
    @Override
    public JwtResponseDto reIssueAccessToken(ReIssueAccessTokenCommand reIssueAccessTokenCommand) {
        // 1. refresh 토큰 도메인을 생성
        RefreshToken refreshToken = RefreshToken.of(reIssueAccessTokenCommand.getRefreshToken());

        // 2. 저장된 refresh 토큰 조회 (여기에는 유저 정보가 포함되어 있음)
        RefreshToken findRefreshToken = findRefreshTokenPort.findRefreshToken(refreshToken);

        // 3. refresh 토큰의 유효성 검증 (회원 정보, 만료 날짜 존재여부, 만료 기간 확인)
        findRefreshToken.validRefreshToken();

        // 4. 토큰 내부의 회원정보 추출
        Member member = findRefreshToken.getMember();

        // 5. 새로운 access 토큰 생성
        String newAccessToken = jwtTokenProvider
                .regenerateAccessToken(member.getEmail(), member.getNickname(), member.getId());

        // 6. 갱신된 JWT 토큰 정보를 DTO에 담아 반환
        return JwtResponseDto.of(newAccessToken, findRefreshToken.getToken());
    }


    /**
     * @return UserDetailsImpl 객체
     * @apiNote SecurityContext에서 인증 정보를 가져와서 UserDetailsImpl 객체를 반환하는 메서드
     */
    private UserDetailsImpl getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new LinkTalkException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return (UserDetailsImpl) authentication.getPrincipal();
    }

}
