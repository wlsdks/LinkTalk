package com.tony.linktalk.config.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.linktalk.adapter.in.web.dto.response.chat.message.ChatMessageResponseDto;
import com.tony.linktalk.application.command.chat.message.CreateChatMessageCommand;
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

        // 세션에서 채팅방 ID를 추출하거나 세션 맵에 저장
        Long chatRoomId = getChatRoomIdFromSession(session);

        // 채팅방 입장 메시지 브로드캐스트
        broadcastToRoom(chatRoomId, "사용자가 채팅방에 입장했습니다.");
    }


    /**
     * @param session WebSocketSession
     * @param message TextMessage
     * @throws Exception Exception
     * @apiNote 메시지를 수신하면 이를 ChatMessageDto로 변환하고 데이터베이스에 저장한 후, 연결된 모든 세션에게 메시지를 브로드캐스트한다.
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            // 수신된 메시지를 추출하고 저장
            String payload = message.getPayload();
            saveChatMessage(payload);

            // HandshakeInterceptor에서 설정한 속성들 가져오기
            String nickname = (String) session.getAttributes().get("nickname");
            Long chatRoomId = getChatRoomIdFromSession(session);

            // 채팅 메시지를 브로드캐스트
            broadcastToRoom(chatRoomId, payload + " from " + nickname);
        } catch (Exception e) {
            // 예외 처리 및 클라이언트에게 에러 메시지 전송
            session.sendMessage(new TextMessage("Error processing message: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * @param session   WebSocketSession
     * @param exception Throwable
     * @throws Exception Exception
     * @apiNote WebSocket 연결 중 에러가 발생하면 해당 세션을 sessions 리스트에서 제거하여 메모리 누수를 방지한다.
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // WebSocket 연결 중 에러가 발생하면 세션을 리스트에서 제거
        sessions.remove(session);
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


    /**
     * @param payload 채팅 메시지
     * @throws JsonProcessingException JsonProcessingException
     * @apiNote 채팅 메시지를 받아서 변환한 다음 저장한다.
     */
    private void saveChatMessage(String payload) throws JsonProcessingException {
        ChatMessageResponseDto chatMessageResponseDto = objectMapper.readValue(payload, ChatMessageResponseDto.class);
        CreateChatMessageCommand command = CreateChatMessageCommand.of(chatMessageResponseDto);
        createChatMessageService.createChatMessage(command);
    }


    /**
     * @param session WebSocketSession
     * @return 채팅방 ID
     * @apiNote WebSocket 세션에서 채팅방 ID를 추출합니다.
     * 일반적으로 WebSocket 세션이 시작될 때 채팅방 ID를 클라이언트에서 전달받아 세션 속성에 저장하는 방식으로 구현할 수 있습니다.
     */
    private Long getChatRoomIdFromSession(WebSocketSession session) {
        // 세션의 속성에서 채팅방 ID를 가져옴 (클라이언트가 연결 시에 채팅방 ID를 전송했다고 가정)
        return (Long) session.getAttributes().get("chatRoomId");
    }


    /**
     * @param chatRoomId 채팅방 ID
     * @param message    메시지
     * @throws Exception Exception
     * @apiNote 이 메서드는 특정 채팅방에 속한 모든 사용자에게 메시지를 전송하는 역할을 한다.
     */
    public void broadcastToRoom(Long chatRoomId, String message) throws Exception {
        for (WebSocketSession session : sessions) {
            // 세션에서 채팅방 ID를 가져와서 동일한 채팅방에 속한 사용자에게만 메시지를 전송
            Long sessionChatRoomId = getChatRoomIdFromSession(session);
            if (sessionChatRoomId != null && sessionChatRoomId.equals(chatRoomId)) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            }
        }
    }

}
