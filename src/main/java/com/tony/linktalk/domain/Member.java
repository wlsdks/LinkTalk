package com.tony.linktalk.domain;

import com.tony.linktalk.exception.ErrorCode;
import com.tony.linktalk.exception.LinkTalkException;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * 회원
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class Member {

    private Long id;               // PK
    private String email;          // 이메일
    private String password;       // 소셜 로그인 사용자는 NULL일 수 있음
    private String name;           // 이름
    private String nickname;       // 닉네임
    private String profilePictureUrl; // 프로필 사진
    private String introduction;   // 자기소개
    private String phoneNumber;    // 전화번호
    private String address;        // 주소
    private LocalDateTime birthDate; // 생년월일
    private String gender;         // 성별
    private String website;        // 웹사이트
    private String statusMessage;  // 상태 메시지
    private String accountStatus;  // 계정 상태 (예: 활성화, 비활성화, 정지 등)
    private LocalDateTime joinedDate; // 가입일
    private LocalDateTime lastLogin;  // 마지막 로그인 시간
    private Jwt jwt;                  // JWT Object 객체

    // factory method
    public static Member of(long id) {
        return Member.builder()
                .id(id)
                .build();
    }


    // factory method
    public static Member fromEmail(String email) {
        return Member.builder()
                .email(email)
                .build();
    }


    // factory method
    public static Member of(Long id, String email, String name, LocalDateTime lastLogin) {
        return Member.builder()
                .id(id)
                .email(email)
                .name(name)
                .lastLogin(lastLogin)
                .build();
    }


    /**
     * 이메일을 변경합니다.
     *
     * @param email 이메일
     */
    public void changeEmail(String email) {
        if (ObjectUtils.isEmpty(email)) {
            throw new LinkTalkException(ErrorCode.CHANGE_EMAIL_VALUE_NOT_FOUND);
        }
        this.email = email;
    }


    /**
     * JWT 객체를 변경합니다.
     *
     * @param jwt JWT 객체
     */
    public void changeMemberInsideJwt(Jwt jwt) {
        if (!ObjectUtils.isEmpty(this.jwt)) {
            throw new LinkTalkException(ErrorCode.MEMBER_JWT_ALREADY_EXIST);
        }
        this.jwt = jwt;
    }


    /**
     * 비밀번호를 변경합니다.
     *
     * @param encode 암호화된 비밀번호
     */
    public void changePassword(String encode) {
        this.password = encode;
    }


    /**
     * 회원 가입시 필수 값 체크
     */
    public void validSignUpMemberData() {
        // 회원 가입시 필수 값 체크
        if (email == null || password == null || name == null) {
            throw new LinkTalkException(ErrorCode.MEMBER_REQUIRED_VALUE);
        }
    }


    /**
     * @param email 이메일
     * @return 이메일이 같으면 true, 다르면 false
     * @apiNote 로그아웃시 token 내부의 이메일과 요청을 보낸 이메일이 같은지 확인
     */
    public boolean isSameEmail(String email) {
        if (ObjectUtils.isEmpty(email)) {
            throw new LinkTalkException(ErrorCode.MEMBER_EMAIL_PARAM_NOT_FOUND);
        }

        if (ObjectUtils.isEmpty(this.email)) {
            throw new LinkTalkException(ErrorCode.MEMBER_INNER_EMAIL_NOT_FOUND);
        }

        if (isNotSameEmail(email)) {
            throw new LinkTalkException(ErrorCode.MEMBER_EMAIL_NOT_MATCH);
        }

        // 같은 이메일이면 true
        return true;
    }


    /**
     * @param email 이메일
     * @return 이메일이 같으면 false, 다르면 true
     * @apiNote 이메일이 같은지 확인
     */
    private boolean isNotSameEmail(String email) {
        return !this.email.equals(email);
    }


    /**
     * @param member Member
     * @apiNote 회원 정보 수정
     */
    public void updateMember(Member member) {
        this.email = member.email;
        this.password = member.password;
        this.name = member.name;
        this.nickname = member.nickname;
        this.profilePictureUrl = member.profilePictureUrl;
        this.introduction = member.introduction;
        this.phoneNumber = member.phoneNumber;
        this.address = member.address;
    }

}
