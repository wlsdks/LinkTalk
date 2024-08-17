package com.tony.linktalk.adapter.out.persistence.adapter;

import com.tony.linktalk.adapter.out.persistence.entity.ChatMessageEntity;
import com.tony.linktalk.adapter.out.persistence.repository.ChatMessageRepository;
import com.tony.linktalk.application.port.out.chat.CreateChatMessagePort;
import com.tony.linktalk.application.port.out.chat.FindChatMessagePort;
import com.tony.linktalk.domain.ChatMessage;
import com.tony.linktalk.mapper.ChatMessageMapper;
import com.tony.linktalk.util.custom.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@PersistenceAdapter
public class ChatMessagePersistenceAdapter implements CreateChatMessagePort, FindChatMessagePort {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;


    /**
     * @param chatMessage ChatMessage
     * @return ChatMessage
     * @apiNote 채팅 메시지를 저장하는 비즈니스 로직
     */
    @Override
    public ChatMessage createChatMessage(ChatMessage chatMessage) {
        return null;
    }


    /**
     * @param roomId Long
     * @return List<ChatMessage>
     * @apiNote 채팅방의 메시지 목록을 조회하는 비즈니스 로직
     */
    @Override
    public Page<ChatMessage> findAllMessagesByRoomId(Long roomId, Pageable pageable) {
        Page<ChatMessageEntity> chatMessageEntities = chatMessageRepository.findChatMessageEntitiesByChatRoomEntity_Id(roomId, pageable);
        return chatMessageEntities.map(chatMessageMapper::entityToDomain);
    }

}
