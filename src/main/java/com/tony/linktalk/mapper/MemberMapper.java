package com.tony.linktalk.mapper;


import com.tony.linktalk.adapter.in.web.dto.request.auth.SignOutRequestDto;
import com.tony.linktalk.adapter.in.web.dto.response.auth.MemberResponseDto;
import com.tony.linktalk.adapter.out.persistence.entity.member.MemberEntity;
import com.tony.linktalk.application.command.auth.SignInCommand;
import com.tony.linktalk.application.command.auth.SignOutCommand;
import com.tony.linktalk.application.command.auth.SignUpCommand;
import com.tony.linktalk.application.command.member.UpdateMemberCommand;
import com.tony.linktalk.domain.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MemberMapper {

    // 응답 도메인을 회원가입 응답 DTO로 변환
    MemberResponseDto domainToResponseDTO(Member member);

    // 도메인을 엔티티로 변환
    MemberEntity toEntity(Member member);

    // 로그아웃 요청 DTO를 엔티티로 변환
    Member toDomain(SignOutRequestDto logoutRequest);

    // MemberEntity 엔티티를 DTO로 변환
    Member toDomain(MemberEntity memberEntity);

    // 로그인 요청 Command를 도메인으로 변환
    Member commandToDomain(SignInCommand signInCommand);

    // 회원가입 요청 Command를 도메인으로 변환
    Member commandToDomain(SignUpCommand signUpCommand);

    // 로그아웃 요청 Command를 도메인으로 변환
    Member commandToDomain(SignOutCommand signOutCommand);

    // 회원 정보 수정 Command를 도메인으로 변환
    Member commandToDomain(UpdateMemberCommand command);

    // 도메인을 응답 DTO로 변환
    MemberResponseDto domainToResponse(Member updatedMember);

}
