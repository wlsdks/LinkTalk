package com.tony.linktalk.adapter.out.persistence.adapter;

import com.tony.linktalk.application.port.out.chat.CreateChatMessagePort;
import com.tony.linktalk.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChatMessagePersistenceAdapter implements CreateChatMessagePort {


    /**
     * @param chatMessage ChatMessage
     * @return ChatMessage
     * @apiNote 채팅 메시지를 저장하는 비즈니스 로직
     */
    @Override
    public ChatMessage createChatMessage(ChatMessage chatMessage) {
        return null;
    }

}
