package com.tony.linktalk.application.service.chatroom;

import com.tony.linktalk.adapter.in.web.dto.response.chat.room.ChatRoomResponseDto;
import com.tony.linktalk.application.command.chat.room.FindChatRoomCommand;
import com.tony.linktalk.application.port.in.chat.room.FindChatRoomUseCase;
import com.tony.linktalk.application.port.out.chatroom.FindChatRoomPort;
import com.tony.linktalk.domain.ChatRoom;
import com.tony.linktalk.mapper.ChatRoomMapper;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@UseCase
public class FindChatRoomService implements FindChatRoomUseCase {

    private final FindChatRoomPort findChatRoomPort;
    private final ChatRoomMapper chatRoomMapper;

    /**
     * @return List<ChatRoomResponseDto>
     * @apiNote 채팅방 목록을 조회하는 API
     */
    @Override
    public List<ChatRoomResponseDto> findAllChatRooms() {
        List<ChatRoom> chatRooms = findChatRoomPort.findAllChatRooms();
        return chatRoomMapper.domainsToResponseDtos(chatRooms);
    }


    /**
     * @param command 커맨드 객체
     * @return List<ChatRoomResponseDto>
     * @apiNote 특정 회원이 속한 채팅방 목록을 조회하는 API
     */
    @Override
    public List<ChatRoomResponseDto> findChatRoomsByMemberId(FindChatRoomCommand command) {
        List<ChatRoom> chatRooms = findChatRoomPort.findChatRoomsByMemberId(command.getMemberId());
        return chatRoomMapper.domainsToResponseDtos(chatRooms);
    }

}
