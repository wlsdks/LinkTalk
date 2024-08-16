package com.tony.linktalk.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * 웹소켓 설정을 위한 클래스
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    /**
     * @param registry WebSocketHandlerRegistry
     * @apiNote WebSocketHandlerRegistry를 통해 WebSocketHandler를 등록
     * HttpSessionHandshakeInterceptor는 WebSocket 핸드셰이크 과정에서 기존의 HttpSession 정보를 WebSocket 세션으로 복사해주는 역할을 합니다.
     * 이렇게 하면 WebSocket 세션에서도 HttpSession에 저장된 속성에 접근할 수 있습니다.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws/chat")
                .addInterceptors(
                        new HttpSessionHandshakeInterceptor(), // HttpSession 정보를 WebSocket 세션에 복사
                        jwtHandshakeInterceptor                // JWT 토큰을 검증하는 인터셉터 추가
                )
                .setAllowedOrigins("*");                       // 이 설정은 개발 중에만 사용하고, 배포 시에는 보안을 강화해야 합니다.
//                .setAllowedOrigins("https://your-allowed-origin.com"); // 실제 서비스에서 허용할 도메인만 설정
    }

}
