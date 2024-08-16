package com.tony.linktalk.application.command.auth.token;

import com.tony.linktalk.adapter.in.web.dto.request.auth.token.ReIssueAccessTokenRequestDto;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReIssueAccessTokenCommand {

    private String refreshToken;


    // factory method
    public static ReIssueAccessTokenCommand of(ReIssueAccessTokenRequestDto request) {
        return ReIssueAccessTokenCommand.builder()
                .refreshToken(request.getRefreshToken())
                .build();
    }

}
