package com.tony.linktalk.application.service.chatroom;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatRoomDto;
import com.tony.linktalk.application.command.CreateChatRoomCommand;
import com.tony.linktalk.application.port.in.chatroom.CreateChatRoomUseCase;
import com.tony.linktalk.application.port.out.chatroom.CreateChatRoomPort;
import com.tony.linktalk.domain.ChatRoom;
import com.tony.linktalk.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateChatRoomService implements CreateChatRoomUseCase {

    private final CreateChatRoomPort createChatRoomPort;
    private final ChatRoomMapper chatRoomMapper;


    /**
     * @param command CreateChatRoomCommand
     * @return ResponseChatRoomDto
     * @apiNote 1:1 DM 채팅방을 생성한다.
     */
    @Override
    public ResponseChatRoomDto createDmChatRoom(CreateChatRoomCommand command) {
        ChatRoom chatRoom = chatRoomMapper.commandToDomain(command);

//         todo: security에서 id를 뽑아내서 추가
//        SecurityUtil.getMemberId();
//        chatRoom.changeCreatorId(1L);

        ChatRoom savedChatRoom = createChatRoomPort.createDmChatRoom(chatRoom);
        return chatRoomMapper.domainToResponseDto(savedChatRoom);
    }

}
