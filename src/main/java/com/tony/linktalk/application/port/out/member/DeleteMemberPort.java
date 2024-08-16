package com.tony.linktalk.application.port.out.member;

import com.tony.linktalk.domain.Member;

public interface DeleteMemberPort {

    Boolean deleteMemberById(Member member);

}