package com.tony.linktalk.application.port.out.member;

import com.tony.linktalk.domain.Member;

public interface FindMemberPort {

    Member findMemberById(Long memberId);

    Member findMember(Member member);

    Member findMemberByEmail(String email);

}
