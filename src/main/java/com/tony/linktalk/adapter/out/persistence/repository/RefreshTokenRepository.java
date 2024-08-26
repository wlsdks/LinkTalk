package com.tony.linktalk.adapter.out.persistence.repository;

import com.tony.linktalk.adapter.out.persistence.entity.member.MemberEntity;
import com.tony.linktalk.adapter.out.persistence.entity.member.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> findRefreshTokenEntityByToken(String refreshToken);

    void deleteByMemberEntity(MemberEntity memberEntity);

}
