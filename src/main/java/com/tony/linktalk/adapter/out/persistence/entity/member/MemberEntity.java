package com.tony.linktalk.adapter.out.persistence.entity.member;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;               // PK

    @Column(unique = true, nullable = false)
    private String email;          // 이메일

    @Column(name = "password")
    private String password;       // 소셜 로그인 사용자는 NULL일 수 있음

    @Column(name = "name")
    private String name;           // 이름

    @Column(name = "nickname")
    private String nickname;       // 닉네임

    @Column(name = "profile_picture_url")
    private String profilePictureUrl; // 프로필 사진

    @Column(name = "introduction")
    private String introduction;   // 자기소개

    @Column(name = "phone_number")
    private String phoneNumber;    // 전화번호

    @Column(name = "address")
    private String address;        // 주소

    @Column(name = "birth_date")
    private LocalDateTime birthDate; // 생년월일

    @Column(name = "gender")
    private String gender;         // 성별

    @Column(name = "website")
    private String website;        // 웹사이트

    @Column(name = "status_message")
    private String statusMessage;  // 상태 메시지

    @Column(name = "account_status")
    private String accountStatus;  // 계정 상태 (예: 활성화, 비활성화, 정지 등)

    @Column(name = "joined_date")
    private LocalDateTime joinedDate; // 가입일

    @Column(name = "last_login")
    private LocalDateTime lastLogin;  // 마지막 로그인 시간

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberEntity memberEntity)) return false;
        return id != null && Objects.equals(getId(), memberEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
