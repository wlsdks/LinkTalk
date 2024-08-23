package com.tony.linktalk.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageType;
import com.tony.linktalk.config.websocket.dto.ChatWebSocketMessage;
import com.tony.linktalk.util.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ChatWebSocketHandlerTest {

    @LocalServerPort
    private int port;

    private WebSocketClient webSocketClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;

    @BeforeEach
    void setUp() {
        webSocketClient = new StandardWebSocketClient();
    }

    @DisplayName("WebSocket 메시지 전송 및 수신 테스트")
    @Test
    void testWebSocketConnectionAndMessageHandling() throws Exception {
        // given
        String fakeToken = jwtTokenProvider.generateAccessToken("test@test.com", "testNickname", 1L);
        Long chatRoomId = 1L;

        // WebSocket 요청에 토큰과 chatRoomId 추가
        String wsUrl = "ws://localhost:" + port + "/ws/chat?token=" + fakeToken + "&chatRoomId=" + chatRoomId;

        // when
        CompletableFuture<WebSocketSession> futureSession = webSocketClient.doHandshake(chatWebSocketHandler, wsUrl).completable();
        WebSocketSession session = futureSession.get(3, TimeUnit.SECONDS);

        // 메시지 전송
        String testMessageContent = "Hello, this is a test message";
        String jsonMessage = objectMapper.writeValueAsString(ChatWebSocketMessage.of(
                ChatMessageType.TEXT, 1L, 1L, testMessageContent
        ));
        session.sendMessage(new TextMessage(jsonMessage));

        // then
        assertEquals(testMessageContent, "사용자가 채팅방에 입장했습니다.");
    }

}