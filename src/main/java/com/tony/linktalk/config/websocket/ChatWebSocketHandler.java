package com.tony.linktalk.config.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.linktalk.adapter.in.web.dto.request.chat.message.ChatMessageRequestDto;
import com.tony.linktalk.application.command.chat.message.CreateChatMessageCommand;
import com.tony.linktalk.application.port.in.chat.message.CreateChatMessageUseCase;
import com.tony.linktalk.config.websocket.dto.ChatWebSocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocketHandler를 상속받아 WebSocket 메시지를 처리하는 핸들러
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 현재 연결된 모든 WebSocket 세션을 저장합니다. 이 리스트는 스레드 safe 하게 여러 클라이언트 세션을 저장할 수 있다.
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper;
    private final CreateChatMessageUseCase createChatMessageUseCase;


    /**
     * @param session WebSocketSession
     * @throws Exception Exception
     * @apiNote 새로운 WebSocket 연결이 생성될 때마다 해당 세션을 sessions 리스트에 추가한다.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 세션의 속성이 초기화될 때까지 대기 (비동기 환경에서 안전성을 보장)
        synchronized (session) {
            log.debug("Attributes in session: {}", session.getAttributes());
            Object chatRoomId = session.getAttributes().get("chatRoomId");
            log.debug("ChatRoomId in session: {}", chatRoomId);

            if (chatRoomId != null) {
                sessions.add(session);
                sendMessageToReceiver("사용자가 채팅방에 입장했습니다.");
            } else {
                log.error("ChatRoomId is null in afterConnectionEstablished.");
            }
        }
    }


    /**
     * @param session WebSocketSession
     * @param message TextMessage
     * @throws Exception Exception
     * @apiNote 메시지를 수신하면 이를 ChatMessageDto로 변환하고 데이터베이스에 저장한 후, 연결된 수신자에게만 메시지를 브로드캐스트한다.
     * 이 메서드는 애플리케이션 코드에서 WebSocket 서버가 수신한 메시지를 처리하기 위해 존재한다.
     * 클라이언트로부터 수신된 WebSocket 메시지를 분석하고, 메시지 유형에 따라 적절한 로직(예: 메시지 저장, 브로드캐스트)을 수행한다.
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            // 수신된 메시지를 ChatWebSocketMessage 객체로 변환
            String payload = message.getPayload();
            ChatWebSocketMessage chatWebSocketMessage = ChatWebSocketMessage.of(payload);

            // HandshakeInterceptor에서 설정한 속성들 가져오기
            String nickname = (String) session.getAttributes().get("nickname");
            Long chatRoomId = extractChatRoomIdFrom(session);
            Long receiverId = extractReceiverIdFrom(session);

            // ChatWebSocketMessage에 채팅방 ID, 수신자 ID 설정
            chatWebSocketMessage.changeChatRoomId(chatRoomId);
            chatWebSocketMessage.changeReceiverId(receiverId);

            // 메시지 타입에 따른 처리
            String broadcastMessage = switch (chatWebSocketMessage.getChatMessageType()) {
                case TEXT -> chatWebSocketMessage.getContent() + " from " + nickname;
                case FILE -> "파일이 전송되었습니다: " + chatWebSocketMessage.getContent();
                case IMAGE -> "이미지가 전송되었습니다: " + chatWebSocketMessage.getContent();
                case VIDEO -> "비디오가 전송되었습니다: " + chatWebSocketMessage.getContent();
                case AUDIO -> "오디오가 전송되었습니다: " + chatWebSocketMessage.getContent();
                default -> "알 수 없는 메시지 타입입니다.";
            };

            // 메시지를 DB에 저장
            saveChatMessage(chatWebSocketMessage);

            // DM인지 확인하여 수신자에게만 전송
            sendMessageToReceiver(broadcastMessage);
            log.info("User {} in chat room {}: {}", nickname, chatRoomId, broadcastMessage);
        } catch (Exception e) {
            // 로깅
            log.error("Error processing WebSocket message", e);
            // 클라이언트에 전송할 메시지 (일반적인 오류 메시지)
            session.sendMessage(new TextMessage("An error occurred while processing the message."));
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
     * @param message 메시지
     * @throws Exception Exception
     * @apiNote 채팅방에 메시지를 브로드캐스트합니다.
     */
    public void sendMessageToReceiver(String message) throws Exception {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                synchronized (session) { // 동기화 블록을 추가하여 동시 접근을 방지
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        log.error("Error sending WebSocket message", e);
                    } catch (IllegalStateException e) {
                        log.error("IllegalStateException: WebSocket session is not in a valid state for sending a message", e);
                    }
                }
            } else {
                log.warn("Attempted to send message on a closed WebSocket session.");
            }
        }
    }

    /**
     * @param chatWebSocketMessage ChatWebSocketMessage
     * @throws JsonProcessingException JsonProcessingException
     * @apiNote 채팅 메시지를 받아서 변환한 다음 저장한다.
     */
    private void saveChatMessage(ChatWebSocketMessage chatWebSocketMessage) {
        // ChatMessageResponseDto로 변환
        ChatMessageRequestDto chatMessageRequestDto = ChatMessageRequestDto.of(chatWebSocketMessage);

        // CreateChatMessageCommand로 변환
        CreateChatMessageCommand command = CreateChatMessageCommand.of(chatMessageRequestDto);

        // 메시지 저장 로직 호출
        // todo: 이걸 Kafka로 처리해 보자
        createChatMessageUseCase.createChatMessage(command);
    }


    /**
     * @param session WebSocketSession
     * @return 채팅방 ID
     * @apiNote WebSocket 세션에서 채팅방 ID를 추출합니다.
     * 일반적으로 WebSocket 세션이 시작될 때 채팅방 ID를 클라이언트에서 전달받아 세션 속성에 저장하는 방식으로 구현할 수 있습니다.
     */
    private Long extractChatRoomIdFrom(WebSocketSession session) {
        Object chatRoomId = session.getAttributes().get("chatRoomId");
        if (chatRoomId == null) {
            log.error("Chat room ID is null");
            return null;
        }
        if (chatRoomId instanceof Long) {
            return (Long) chatRoomId;
        } else if (chatRoomId instanceof String) {
            try {
                return Long.parseLong((String) chatRoomId);
            } catch (NumberFormatException e) {
                log.error("Chat room ID is not a valid Long value: {}", chatRoomId);
            }
        }
        log.error("Chat room ID is not of type Long or String");
        return null;
    }


    /**
     * @param session WebSocketSession
     * @return 수신자 ID
     * @apiNote 세션에서 수신자 ID를 가져옵니다. (jwtHandshakeInterceptor에서 설정한 속성)
     */
    private static Long extractReceiverIdFrom(WebSocketSession session) {
        Object memberId = session.getAttributes().get("memberId");
        if (memberId == null) {
            log.error("Receiver ID is null");
            return null;
        }
        if (memberId instanceof Long) {
            return (Long) memberId;
        } else if (memberId instanceof String) {
            try {
                return Long.parseLong((String) memberId);
            } catch (NumberFormatException e) {
                log.error("Receiver ID is not a valid Long value: {}", memberId);
            }
        }
        log.error("Receiver ID is not of type Long or String");
        return null;
    }

}
