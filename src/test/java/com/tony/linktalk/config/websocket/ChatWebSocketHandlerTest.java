package com.tony.linktalk.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageType;
import com.tony.linktalk.application.port.in.chat.message.CreateChatMessageUseCase;
import com.tony.linktalk.config.websocket.dto.ChatWebSocketMessage;
import com.tony.linktalk.util.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

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

    @MockBean
    private CreateChatMessageUseCase createChatMessageUseCase;

    @BeforeEach
    void setUp() {
        webSocketClient = new StandardWebSocketClient();
    }


    @DisplayName("ChatWebSocketHandler 메시지 전송 및 수신 테스트")
    @Test
    void testWebSocketConnectionAndMessageHandling() throws Exception {
        // given
        String fakeToken = jwtTokenProvider.generateAccessToken("test@test.com", "testNickname", 1L);
        Long chatRoomId = 1L;

        // WebSocket 요청에 토큰과 chatRoomId 추가
        String wsUrl = "ws://localhost:" + port + "/ws/chat?token=" + fakeToken + "&chatRoomId=" + chatRoomId;

        // CountDownLatch로 비동기 작업을 동기화
        CountDownLatch latch = new CountDownLatch(2);  // 메시지 2개를 수신할 때까지 대기
        final List<String> receivedMessages = new ArrayList<>();

        // CreateChatMessageUseCase의 mock 동작 정의
        doNothing().when(createChatMessageUseCase).createChatMessage(any());

        // WebSocketHandler 정의
        WebSocketHandler webSocketHandler = new TextWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                receivedMessages.add(message.getPayload());
                latch.countDown();
            }
        };

        // WebSocketHttpHeaders 생성 (필요 시)
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

        // WebSocketClient를 통해 연결을 맺고 세션을 가져옴
        CompletableFuture<WebSocketSession> futureSession = webSocketClient.execute(webSocketHandler, headers, URI.create(wsUrl));
        WebSocketSession session = futureSession.get(3, TimeUnit.SECONDS);

        // 테스트 메시지 전송
        String testMessageContent = "Hello, this is a test message";
        String jsonMessage = objectMapper.writeValueAsString(ChatWebSocketMessage.of(
                ChatMessageType.TEXT, 1L, 1L, testMessageContent
        ));
        session.sendMessage(new TextMessage(jsonMessage));

        // 수신된 메시지 확인 (카운트다운이 성공적으로 완료되었는지 확인하고, 그렇지 않은 경우 예외를 던져 오류를 처리합니다.)
        boolean awaitSuccess = latch.await(3, TimeUnit.SECONDS);
        if (!awaitSuccess) {
            throw new IllegalStateException("Latch countdown timed out before all messages were received.");
        }

        // 첫 번째 메시지는 입장 메시지일 수 있음
        assertEquals("사용자가 채팅방에 입장했습니다.", receivedMessages.get(0));

        // 두 번째 메시지의 JSON 파싱하여 content 필드 추출
        String receivedJson = receivedMessages.get(1);
        ChatWebSocketMessage receivedMessage = objectMapper.readValue(receivedJson, ChatWebSocketMessage.class);

        // 추출된 content와 기대 메시지 비교
        assertEquals(testMessageContent, receivedMessage.getContent());
    }

}