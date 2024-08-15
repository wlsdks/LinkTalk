package com.tony.linktalk.config.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * WebSocketHandler를 상속받아 WebSocket 메시지를 처리하는 핸들러
 */
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    /**
     * @param session WebSocketSession
     * @param message TextMessage
     * @throws Exception Exception
     * @apiNote WebSocket 세션을 통해 메시지를 수신하면 호출
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 수신한 메시지를 payload로부터 추출
        String payload = message.getPayload();
        // 메시지 처리 로직 (Kafka로 전달 등)
        session.sendMessage(new TextMessage("Message received: " + payload));
    }

}
