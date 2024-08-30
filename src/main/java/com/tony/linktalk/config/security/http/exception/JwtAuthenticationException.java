package com.tony.linktalk.config.security.http.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * JWT 관련 인증 예외 처리 클래스 (커스텀 예외)
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

}
