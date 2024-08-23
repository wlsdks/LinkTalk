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

        // CountDownLatch로 비동기 작업을 동기화
        CountDownLatch latch = new CountDownLatch(1);

        CompletableFuture<WebSocketSession> futureSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                // 핸들러가 수신한 메시지를 확인하도록 로직 추가
                session.sendMessage(new TextMessage("사용자가 채팅방에 입장했습니다."));
                latch.countDown(); // 동기화 완료 후 래치 카운트 감소
            }
        }, wsUrl).completable();

        WebSocketSession session = futureSession.get(3, TimeUnit.SECONDS);

        // Latch 대기
        latch.await();

        // 메시지 전송
        String testMessageContent = "Hello, this is a test message";
        String jsonMessage = objectMapper.writeValueAsString(ChatWebSocketMessage.of(
                ChatMessageType.TEXT, 1L, 1L, testMessageContent
        ));
        session.sendMessage(new TextMessage(jsonMessage));

        // 메시지 수신과 비교
        assertEquals("Hello, this is a test message", testMessageContent);
    }

}