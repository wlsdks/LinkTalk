package com.tony.linktalk.adapter.out.persistence.repository;

import com.tony.linktalk.adapter.out.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findMemberEntityByEmail(String email);

}
