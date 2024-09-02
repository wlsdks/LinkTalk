package com.tony.linktalk.adapter.in.web.dto.response.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * JWT 토큰 발급 응답 DTO
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class JwtResponseDto {

    private String accessToken;
    private String refreshToken;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public static JwtResponseDto of(String accessToken, String refreshToken, String email, Collection<? extends GrantedAuthority> authorities) {
        return new JwtResponseDto(accessToken, refreshToken, email, authorities);
    }

    public static JwtResponseDto of(String accessToken, String refreshToken, String email) {
        return new JwtResponseDto(accessToken, refreshToken, email, null);
    }

    public static JwtResponseDto of(String accessToken, String refreshToken) {
        return new JwtResponseDto(accessToken, refreshToken, null, null);
    }

}
