package com.tony.linktalk.adapter.in.web.dto.request.auth;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignInRequestDto {

    private String email;
    private String password;

}
