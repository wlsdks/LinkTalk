package com.tony.linktalk.config.websocket;

import com.tony.linktalk.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * @param request    WebSocket 연결 요청
     * @param response   WebSocket 연결 응답
     * @param wsHandler  WebSocket 핸들러
     * @param attributes WebSocket 핸들러에 전달할 속성
     * @return JWT 토큰이 유효한 경우 true, 그렇지 않은 경우 false
     * @throws Exception 예외
     * @apiNote WebSocket 연결 시, JWT 토큰을 추출하여 유효성 검사
     */
    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) throws Exception {
        // WebSocket 연결 시, JWT 토큰을 추출하여 유효성 검사
        String query = request.getURI().getQuery();
        String token = UriComponentsBuilder.fromUriString("?" + query)
                .build()
                .getQueryParams()
                .getFirst("token");

        // JWT 토큰이 유효한 경우, 이메일을 추출하여 attributes에 추가
        if (token != null && jwtTokenProvider.validateJwtToken(token)) {
            Claims claims = jwtTokenProvider.getClaimsFromJwtToken(token);
            attributes.put("email", claims.getSubject());
            return true;
        }

        return false; // JWT가 유효하지 않으면 핸드셰이크 거부
    }


    /**
     * @param request   WebSocket 연결 요청
     * @param response  WebSocket 연결 응답
     * @param wsHandler WebSocket 핸들러
     * @param exception 예외
     * @apiNote WebSocket 연결 후 처리
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // Do nothing
    }

}
