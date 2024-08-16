package com.tony.linktalk.application.command.auth;

import com.tony.linktalk.adapter.in.web.dto.request.auth.SignOutRequestDto;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignOutCommand {

    private String email;


    // factory method
    public static SignOutCommand of(SignOutRequestDto signOutRequestDTO) {
        return SignOutCommand.builder()
                .email(signOutRequestDTO.getEmail())
                .build();
    }

}
