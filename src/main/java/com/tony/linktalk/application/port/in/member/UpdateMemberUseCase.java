package com.tony.linktalk.application.port.in.member;

import com.tony.linktalk.adapter.in.web.dto.response.auth.MemberResponseDto;
import com.tony.linktalk.application.command.member.UpdateMemberCommand;

public interface UpdateMemberUseCase {

    MemberResponseDto updateMember(UpdateMemberCommand command);

}
