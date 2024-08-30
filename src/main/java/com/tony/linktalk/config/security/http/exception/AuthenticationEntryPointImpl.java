package com.tony.linktalk.config.security.http.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * security 인증 예외 처리 클래스 (filterChain으로 이 클래스를 등록해서 사용)
 * 이 클래스는 인증되지 않은 요청이 들어왔을 때, HTTP 401 Unauthorized 응답을 생성하는 역할을 한다.
 * Spring Security 설정에서 AuthenticationEntryPoint로 등록되어 있으므로, 인증 과정에서 JwtAuthenticationException과 같은 AuthenticationException이 발생하면 이 클래스의 commence 메서드가 호출된다.
 * commence 메서드는 JSON 형식으로 응답을 반환하며, 클라이언트에게 적절한 에러 메시지를 전달한다.
 */
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * @param request       HttpServletRequest 객체
     * @param response      HttpServletResponse 객체
     * @param authException 발생한 인증 예외
     * @throws IOException 입출력 예외가 발생할 때
     * @apiNote 인증되지 않은 요청에 대한 처리 메서드. (AuthenticationEntryPoint 인터페이스의 commence 메서드 구현)
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        // 1. 응답의 콘텐츠 타입을 JSON으로 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 2. 응답 상태 코드를 401 Unauthorized로 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 3. 응답 본문에 포함할 데이터 맵 생성 (불변 Map)
        Map<String, Object> body = Map.of(
                "status", HttpServletResponse.SC_UNAUTHORIZED,
                "error", "Unauthorized",
                "message", authException.getMessage(),
                "path", request.getServletPath()
        );

        // 4. ObjectMapper를 사용하여 응답의 출력 스트림에 JSON 데이터 작성
        mapper.writeValue(response.getOutputStream(), body);
    }

}
