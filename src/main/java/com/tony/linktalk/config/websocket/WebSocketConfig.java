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


    /**
     * @param registry WebSocketHandlerRegistry
     * @apiNote WebSocketHandlerRegistry를 통해 WebSocketHandler를 등록
     * HttpSessionHandshakeInterceptor는 WebSocket 핸드셰이크 과정에서 기존의 HttpSession 정보를 WebSocket 세션으로 복사해주는 역할을 합니다.
     * 이렇게 하면 WebSocket 세션에서도 HttpSession에 저장된 속성에 접근할 수 있습니다.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws/chat")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*"); // 클라이언트에서 웹 소켓 서버에 요청하는 모든 요청을 수락, CORS 방지
        // todo: 실제 서비스에서는 "*"으로 하면 안된다. 스프링에서 웹소켓을 사용할 때, same-origin만 허용하는 것이 기본정책이다.
    }

}
