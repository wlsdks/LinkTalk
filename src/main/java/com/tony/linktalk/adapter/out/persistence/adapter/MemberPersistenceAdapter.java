package com.tony.linktalk.adapter.out.persistence.adapter;

import com.tony.linktalk.adapter.out.persistence.entity.MemberEntity;
import com.tony.linktalk.adapter.out.persistence.repository.MemberRepository;
import com.tony.linktalk.application.port.out.member.CreateMemberPort;
import com.tony.linktalk.application.port.out.member.DeleteMemberPort;
import com.tony.linktalk.application.port.out.member.FindMemberPort;
import com.tony.linktalk.application.port.out.member.UpdateMemberPort;
import com.tony.linktalk.domain.Member;
import com.tony.linktalk.exception.ErrorCode;
import com.tony.linktalk.exception.LinkTalkException;
import com.tony.linktalk.mapper.MemberMapper;
import com.tony.linktalk.util.custom.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

/**
 * MemberAdapter
 */
@RequiredArgsConstructor
@PersistenceAdapter
public class MemberPersistenceAdapter implements CreateMemberPort, FindMemberPort, DeleteMemberPort, UpdateMemberPort {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;


    /**
     * 회원 생성
     *
     * @param member 회원 도메인
     * @return 생성된 회원
     */
    @Override
    public Member createMember(Member member) {
        MemberEntity memberEntity = memberMapper.toEntity(member);
        MemberEntity savedMember = memberRepository.save(memberEntity);
        return memberMapper.toDomain(savedMember);
    }


    /**
     * ID로 회원 조회
     *
     * @param member 회원 도메인
     * @return 조회된 회원
     */
    @Override
    public Member findMember(Member member) {
        MemberEntity memberEntity = memberRepository.findById(member.getId())
                .orElseThrow(() -> new LinkTalkException(ErrorCode.MEMBER_NOT_FOUND));

        return memberMapper.toDomain(memberEntity);
    }


    /**
     * @param email 회원 이메일
     * @return 조회된 회원
     * @apiNote Email로 회원 조회
     */
    @Override
    public Member findMemberByEmail(String email) {
        MemberEntity memberEntity = memberRepository.findMemberEntityByEmail(email)
                .orElseThrow(() -> new LinkTalkException(ErrorCode.MEMBER_NOT_FOUND));

        return Member.of(memberEntity.getId(), memberEntity.getEmail(), memberEntity.getName(), memberEntity.getLastLogin());
    }


    /**
     * @param memberId 회원 ID
     * @return ID로 조회된 회원
     * @apiNote ID로 회원 조회
     */
    @Override
    public Member findMemberById(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new LinkTalkException(ErrorCode.MEMBER_NOT_FOUND));
        return memberMapper.toDomain(memberEntity);
    }


    /**
     * 회원 수정
     *
     * @param member 회원 도메인
     * @return 수정된 회원
     */
    @Override
    public Member updateMember(Member member) {
        MemberEntity memberEntity = memberMapper.toEntity(member);
        MemberEntity updatedMember = memberRepository.save(memberEntity);
        return memberMapper.toDomain(updatedMember);
    }


    /**
     * 회원 삭제
     *
     * @param member 회원 도메인
     * @return 삭제 여부
     */
    @Override
    public Boolean deleteMemberById(Member member) {
        memberRepository.deleteById(member.getId());
        return true;
    }

}
