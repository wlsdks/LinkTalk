package com.tony.linktalk.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.linktalk.application.port.in.chat.message.CreateChatMessageUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.mock;

class ChatWebSocketHandlerTest {

    private ChatWebSocketHandler chatWebSocketHandler;
    private ObjectMapper objectMapper;
    private CreateChatMessageUseCase createChatMessageUseCase;
    private WebSocketSession session;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        createChatMessageUseCase = mock(CreateChatMessageUseCase.class);
        chatWebSocketHandler = new ChatWebSocketHandler(objectMapper, createChatMessageUseCase);
        session = mock(WebSocketSession.class);
    }

}