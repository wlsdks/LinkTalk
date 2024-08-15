package com.tony.linktalk.application.service.chatroom;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatRoomDto;
import com.tony.linktalk.application.command.FindChatRoomCommand;
import com.tony.linktalk.application.port.in.chatroom.FindChatRoomUseCase;
import com.tony.linktalk.application.port.out.chatroom.FindChatRoomPort;
import com.tony.linktalk.domain.ChatRoom;
import com.tony.linktalk.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FindChatRoomService implements FindChatRoomUseCase {

    private final FindChatRoomPort findChatRoomPort;
    private final ChatRoomMapper chatRoomMapper;

    /**
     * @return List<ResponseChatRoomDto>
     * @apiNote 채팅방 목록을 조회하는 API
     */
    @Override
    public List<ResponseChatRoomDto> findAllChatRooms() {
        List<ChatRoom> chatRooms = findChatRoomPort.findAllChatRooms();
        return chatRoomMapper.domainsToResponseDtos(chatRooms);
    }


    /**
     * @param command 커맨드 객체
     * @return List<ResponseChatRoomDto>
     * @apiNote 특정 회원이 속한 채팅방 목록을 조회하는 API
     */
    @Override
    public List<ResponseChatRoomDto> findChatRoomsByMemberId(FindChatRoomCommand command) {
        List<ChatRoom> chatRooms = findChatRoomPort.findChatRoomsByMemberId(command.getMemberId());
        return chatRoomMapper.domainsToResponseDtos(chatRooms);
    }

}
