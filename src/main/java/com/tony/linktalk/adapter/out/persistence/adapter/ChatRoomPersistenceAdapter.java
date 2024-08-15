package com.tony.linktalk.adapter.out.persistence.adapter;

import com.tony.linktalk.adapter.out.persistence.entity.ChatRoomEntity;
import com.tony.linktalk.adapter.out.persistence.repository.ChatRoomRepository;
import com.tony.linktalk.application.port.out.chatroom.CreateChatRoomPort;
import com.tony.linktalk.application.port.out.chatroom.FindChatRoomPort;
import com.tony.linktalk.domain.ChatRoom;
import com.tony.linktalk.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ChatRoomPersistenceAdapter implements FindChatRoomPort, CreateChatRoomPort {

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


    /**
     * @param chatRoom ChatRoom
     * @return ChatRoom
     * @apiNote 1:1 DM 채팅방을 생성한다.
     */
    @Override
    public ChatRoom createDmChatRoom(ChatRoom chatRoom) {
        ChatRoomEntity chatRoomEntity = chatRoomMapper.domainToEntity(chatRoom);
        ChatRoomEntity savedChatRoomEntity = chatRoomRepository.save(chatRoomEntity);
        return chatRoomMapper.entityToDomain(savedChatRoomEntity);
    }

}
