package com.tony.linktalk.application.service.member;

import com.tony.linktalk.adapter.in.web.dto.response.auth.MemberResponseDto;
import com.tony.linktalk.application.command.member.UpdateMemberCommand;
import com.tony.linktalk.application.port.in.member.UpdateMemberUseCase;
import com.tony.linktalk.application.port.out.member.FindMemberPort;
import com.tony.linktalk.application.port.out.member.UpdateMemberPort;
import com.tony.linktalk.domain.Member;
import com.tony.linktalk.mapper.MemberMapper;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@UseCase
public class UpdateMemberService implements UpdateMemberUseCase {

    private final FindMemberPort findMemberPort;
    private final UpdateMemberPort updateMemberPort;
    private final MemberMapper memberMapper;

    /**
     * @param command UpdateMemberCommand
     * @return Member
     * @apiNote 회원 정보 수정
     */
    @Transactional
    @Override
    public MemberResponseDto updateMember(UpdateMemberCommand command) {
        // command를 domain으로 변환
        Member member = memberMapper.commandToDomain(command);

        // 회원 정보 조회
        Member findMember = findMemberPort.findMemberById(member.getId());

        // 회원 정보 수정
        findMember.updateMember(member);

        // 회원 정보 저장
        Member updatedMember = updateMemberPort.updateMember(findMember);

        // domain to response
        return memberMapper.domainToResponse(updatedMember);
    }

}
