package com.tony.linktalk.adapter.out.persistence.adapter;

import com.tony.linktalk.adapter.out.persistence.entity.ChatRoomEntity;
import com.tony.linktalk.adapter.out.persistence.repository.ChatRoomRepository;
import com.tony.linktalk.application.port.out.chatroom.FindChatRoomPort;
import com.tony.linktalk.domain.ChatRoom;
import com.tony.linktalk.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ChatRoomPersistenceAdapter implements FindChatRoomPort {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;


    /**
     * @return List<ChatRoom>
     * @apiNote 채팅방 목록을 조회한다.
     */
    @Override
    public List<ChatRoom> findAllChatRooms() {
        List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.findAll();
        return chatRoomMapper.entitiesToDomains(chatRoomEntities);
    }

    /**
     * @param memberId Long
     * @return List<ChatRoom>
     * @apiNote 특정 회원이 속한 채팅방 목록을 조회한다.
     */
    @Override
    public List<ChatRoom> findChatRoomsByMemberId(Long memberId) {
        List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.findChatRoomEntitiesByCreatorId(memberId);
        return chatRoomMapper.entitiesToDomains(chatRoomEntities);
    }

}
