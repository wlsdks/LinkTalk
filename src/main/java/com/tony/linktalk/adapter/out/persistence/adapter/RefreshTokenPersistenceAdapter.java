package com.tony.linktalk.adapter.out.persistence.adapter;

import com.tony.linktalk.adapter.out.persistence.entity.MemberEntity;
import com.tony.linktalk.adapter.out.persistence.entity.RefreshTokenEntity;
import com.tony.linktalk.adapter.out.persistence.repository.RefreshTokenRepository;
import com.tony.linktalk.application.port.out.refreshtoken.CreateRefreshTokenPort;
import com.tony.linktalk.application.port.out.refreshtoken.DeleteRefreshTokenPort;
import com.tony.linktalk.application.port.out.refreshtoken.FindRefreshTokenPort;
import com.tony.linktalk.application.port.out.refreshtoken.UpdateRefreshTokenPort;
import com.tony.linktalk.domain.Member;
import com.tony.linktalk.domain.RefreshToken;
import com.tony.linktalk.exception.ErrorCode;
import com.tony.linktalk.exception.LinkTalkException;
import com.tony.linktalk.mapper.MemberMapper;
import com.tony.linktalk.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 리프레시 토큰 관련 비즈니스 로직을 처리하는 어댑터 클래스
 */
@RequiredArgsConstructor
@Component
public class RefreshTokenPersistenceAdapter implements CreateRefreshTokenPort, FindRefreshTokenPort, DeleteRefreshTokenPort, UpdateRefreshTokenPort {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberMapper memberMapper;
    private final RefreshTokenMapper refreshTokenMapper;


    /**
     * @param refreshToken RefreshToken
     * @return 생성된 RefreshToken
     * @apiNote RefreshToken 생성
     */
    @Override
    public RefreshToken createRefreshToken(RefreshToken refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenMapper.domainToEntity(refreshToken);
        validRefreshTokenEntity(refreshTokenEntity);
        // RefreshToken 생성 및 저장
        RefreshTokenEntity savedRefreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);
        return refreshTokenMapper.entityToDomain(savedRefreshTokenEntity);
    }


    /**
     * @param member 회원
     * @return 삭제 여부
     * @apiNote RefreshToken 삭제
     */
    @Override
    public Boolean deleteRefreshToken(Member member) {
        MemberEntity memberEntity = memberMapper.toEntity(member);
        validMemberEntity(memberEntity);
        // 회원의 RefreshToken 삭제
        refreshTokenRepository.deleteByMemberEntity(memberEntity);
        return true;
    }


    /**
     * @param refreshToken RefreshToken
     * @return 조회된 RefreshToken
     * @apiNote RefreshToken 조회
     */
    @Override
    public RefreshToken findRefreshToken(RefreshToken refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenMapper.domainToEntity(refreshToken);
        validRefreshTokenEntity(refreshTokenEntity);

        // 이메일을 통해 RefreshToken 조회
        String email = refreshTokenEntity.getMemberEntity().getEmail();
        RefreshTokenEntity findRefreshTokenEntity = refreshTokenRepository.findRefreshTokenEntityByMemberEntity_Email(email)
                .orElseThrow(() -> new LinkTalkException(ErrorCode.DATA_NOT_FOUND));

        return refreshTokenMapper.entityToDomain(findRefreshTokenEntity);
    }


    /**
     * @param refreshToken RefreshToken
     * @return 갱신된 RefreshToken
     * @apiNote RefreshToken 갱신
     */
    @Override
    public RefreshToken updateRefreshToken(RefreshToken refreshToken) {
        return null;
    }


    /**
     * @param refreshTokenEntity RefreshTokenEntity
     * @apiNote RefreshTokenEntity 유효성 검사
     */
    private void validRefreshTokenEntity(RefreshTokenEntity refreshTokenEntity) {
        if (ObjectUtils.isEmpty(refreshTokenEntity)) throw new LinkTalkException(ErrorCode.DATA_NOT_FOUND);
    }


    /**
     * @param memberEntity MemberEntity
     * @apiNote MemberEntity 유효성 검사
     */
    private void validMemberEntity(MemberEntity memberEntity) {
        if (ObjectUtils.isEmpty(memberEntity)) throw new LinkTalkException(ErrorCode.DATA_NOT_FOUND);
    }

}
