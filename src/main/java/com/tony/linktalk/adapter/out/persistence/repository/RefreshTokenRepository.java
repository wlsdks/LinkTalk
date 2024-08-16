package com.tony.linktalk.adapter.out.persistence.repository;

import com.tony.linktalk.adapter.out.persistence.entity.MemberEntity;
import com.tony.linktalk.adapter.out.persistence.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> findRefreshTokenEntityByMemberEntity_Email(String email);

    void deleteByMemberEntity(MemberEntity memberEntity);

}
