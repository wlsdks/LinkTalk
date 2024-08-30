package com.tony.linktalk.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageType;
import com.tony.linktalk.application.port.in.chat.message.CreateChatMessageUseCase;
import com.tony.linktalk.config.websocket.dto.ChatWebSocketMessageDto;
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


    /**
     * @apiNote 테스트 코드에서 별도의 handleTextMessage를 정의하는가?
     * 1. 테스트에서의 역할:
     * 테스트는 서버와의 WebSocket 통신이 예상대로 동작하는지를 확인하는 것이 목적이다.
     * 클라이언트 측에서는 서버로부터 수신된 메시지를 받아야 하며, 이를 receivedMessages에 추가함으로써 나중에 수신된 메시지를 검증할 수 있게 된다.
     * <p>
     * 2. 독립적인 테스트 환경:
     * ChatWebSocketHandler의 실제 동작과 무관하게, 테스트 환경에서는 오직 메시지를 수신하고 그 결과를 검증하는 데에 집중해야 한다.
     * 이를 위해 테스트 코드 내에 별도의 TextWebSocketHandler를 정의하여 테스트 목적에 맞는 메시지 처리 로직을 구현한 것이다.
     */
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

        // WebSocketHandler 정의 (이 코드에서 정의된 handleTextMessage 메서드는 테스트 중에 클라이언트 측에서 수신되는 메시지를 처리하기 위해 사용됩니다.)
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
        String jsonMessage = objectMapper.writeValueAsString(ChatWebSocketMessageDto.of(
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

        // 추출된 content와 기대 메시지 비교
        assertEquals("Hello, this is a test message from testNickname", receivedJson);
    }


    @DisplayName("WebSocket 연결 종료 시 세션 관리 테스트")
    @Test
    void testWebSocketSessionClosure() throws Exception {
        // given
        String fakeToken = jwtTokenProvider.generateAccessToken("test@test.com", "testNickname", 1L);
        Long chatRoomId = 1L;
        String wsUrl = "ws://localhost:" + port + "/ws/chat?token=" + fakeToken + "&chatRoomId=" + chatRoomId;

        CountDownLatch latch = new CountDownLatch(1);
        final List<String> receivedMessages = new ArrayList<>();

        WebSocketHandler webSocketHandler = new TextWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                receivedMessages.add(message.getPayload());
                latch.countDown();
            }
        };

        CompletableFuture<WebSocketSession> futureSession = webSocketClient.execute(webSocketHandler, new WebSocketHttpHeaders(), URI.create(wsUrl));
        WebSocketSession session = futureSession.get(3, TimeUnit.SECONDS);

        // 세션 종료
        session.close();

        // 세션이 올바르게 종료되었는지 확인
        assertEquals(false, session.isOpen());

        // WebSocketHandler가 세션을 제대로 관리하고 있는지 확인
        assertEquals(0, chatWebSocketHandler.getSessions().size());
    }


    @DisplayName("잘못된 메시지 형식 처리 테스트")
    @Test
    void testInvalidMessageHandling() throws Exception {
        // given
        String fakeToken = jwtTokenProvider.generateAccessToken("test@test.com", "testNickname", 1L);
        Long chatRoomId = 1L;
        String wsUrl = "ws://localhost:" + port + "/ws/chat?token=" + fakeToken + "&chatRoomId=" + chatRoomId;

        CountDownLatch latch = new CountDownLatch(2);  // 입장 메시지 + 잘못된 메시지 처리 결과를 기다림
        final List<String> receivedMessages = new ArrayList<>();

        // 이건 js로 치면 웹소켓 클라이언트라고 생각하면 된다. ( 서버에서 보낸 메시지를 받아서 처리하는 부분 )
        WebSocketHandler webSocketHandler = new TextWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                receivedMessages.add(message.getPayload());
                latch.countDown();
            }
        };

        CompletableFuture<WebSocketSession> futureSession = webSocketClient.execute(webSocketHandler, new WebSocketHttpHeaders(), URI.create(wsUrl));
        WebSocketSession session = futureSession.get(3, TimeUnit.SECONDS);

        String invalidMessageContent = "Invalid message format";
        session.sendMessage(new TextMessage(invalidMessageContent));

        boolean awaitSuccess = latch.await(3, TimeUnit.SECONDS);
        if (!awaitSuccess) {
            throw new IllegalStateException("Latch countdown timed out before all messages were received.");
        }

        assertEquals("사용자가 채팅방에 입장했습니다.", receivedMessages.get(0));
        assertEquals("An error occurred while processing the message.", receivedMessages.get(1));
    }


    @DisplayName("파일 전송 메시지 처리 테스트")
    @Test
    void testFileTransferMessageHandling() throws Exception {
        // given
        String fakeToken = jwtTokenProvider.generateAccessToken("test@test.com", "testNickname", 1L);
        Long chatRoomId = 1L;
        String wsUrl = "ws://localhost:" + port + "/ws/chat?token=" + fakeToken + "&chatRoomId=" + chatRoomId;

        CountDownLatch latch = new CountDownLatch(2); // 입장 메시지 + 파일 전송 메시지 두 개를 기다림
        final List<String> receivedMessages = new ArrayList<>();

        WebSocketHandler webSocketHandler = new TextWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                receivedMessages.add(message.getPayload());
                latch.countDown();
            }
        };

        CompletableFuture<WebSocketSession> futureSession = webSocketClient.execute(webSocketHandler, new WebSocketHttpHeaders(), URI.create(wsUrl));
        WebSocketSession session = futureSession.get(3, TimeUnit.SECONDS);

        // 파일 전송 메시지 전송
        String fileMessageContent = "file.txt";
        String jsonMessage = objectMapper.writeValueAsString(ChatWebSocketMessageDto.of(
                ChatMessageType.FILE, 1L, 1L, fileMessageContent
        ));
        session.sendMessage(new TextMessage(jsonMessage));

        // 수신된 메시지 확인
        boolean awaitSuccess = latch.await(3, TimeUnit.SECONDS);
        if (!awaitSuccess) {
            throw new IllegalStateException("Latch countdown timed out before all messages were received.");
        }

        // 첫 번째 메시지가 입장 메시지인지 확인
        assertEquals("사용자가 채팅방에 입장했습니다.", receivedMessages.get(0));

        // 두 번째 메시지가 파일 전송 메시지인지 확인
        assertEquals("파일이 전송되었습니다: file.txt", receivedMessages.get(1));
    }


}