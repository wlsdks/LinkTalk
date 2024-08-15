package com.tony.linktalk.domain;


import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Member {

    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String password;

    // factory method
    public static Member of(String username, String nickname, String email, String password) {
        return Member.builder()
                .username(username)
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();
    }

}
