package com.tony.linktalk.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatMessageDto;
import com.tony.linktalk.application.command.CreateChatMessageCommand;
import com.tony.linktalk.application.service.chat.CreateChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocketHandler를 상속받아 WebSocket 메시지를 처리하는 핸들러
 */
@RequiredArgsConstructor
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 현재 연결된 모든 WebSocket 세션을 저장합니다. 이 리스트는 스레드 safe 하게 여러 클라이언트 세션을 저장할 수 있다.
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CreateChatMessageService createChatMessageService;


    /**
     * @param session WebSocketSession
     * @throws Exception Exception
     * @apiNote 새로운 WebSocket 연결이 생성될 때마다 해당 세션을 sessions 리스트에 추가한다.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 새로운 WebSocket 세션이 연결되면 세션을 리스트에 추가
        sessions.add(session);
    }


    /**
     * @param session WebSocketSession
     * @param message TextMessage
     * @throws Exception Exception
     * @apiNote 메시지를 수신하면 이를 ChatMessageDto로 변환하고 데이터베이스에 저장한 후, 연결된 모든 세션에게 메시지를 브로드캐스트한다.
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 수신한 메시지를 payload로부터 추출
        String payload = message.getPayload();

        // ChatMessageDto로 변환
        ResponseChatMessageDto responseChatMessageDto = objectMapper.readValue(payload, ResponseChatMessageDto.class);

        // DTO를 Command로 변환
        CreateChatMessageCommand command = CreateChatMessageCommand.of(responseChatMessageDto);

        // 메시지를 DB에 저장
        createChatMessageService.processChatMessage(command);

        // 메시지를 같은 채팅방의 모든 클라이언트에게 전송
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(payload));
            }
        }
    }


    /**
     * @param session WebSocketSession
     * @param status  CloseStatus
     * @throws Exception Exception
     * @apiNote WebSocket 연결이 종료되면 해당 세션을 sessions 리스트에서 제거하여 메모리 누수를 방지한다.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // WebSocket 세션이 종료되면 세션을 리스트에서 제거
        sessions.remove(session);
    }

}
