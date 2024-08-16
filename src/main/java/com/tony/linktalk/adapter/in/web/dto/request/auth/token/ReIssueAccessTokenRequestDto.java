package com.tony.linktalk.adapter.in.web.dto.request.auth.token;

import lombok.*;

/**
 * JWT 토큰 재발급 요청 DTO
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReIssueAccessTokenRequestDto {

    private String refreshToken; // 리프레시 토큰

}
