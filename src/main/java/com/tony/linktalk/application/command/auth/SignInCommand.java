package com.tony.linktalk.application.command.auth;

import com.tony.linktalk.adapter.in.web.dto.request.auth.SignInRequestDto;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignInCommand {

    private String email;
    private String password;


    // factory method
    public static SignInCommand of(SignInRequestDto signInRequestDTO) {
        return SignInCommand.builder()
                .email(signInRequestDTO.getEmail())
                .password(signInRequestDTO.getPassword())
                .build();
    }

}
