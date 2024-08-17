package com.tony.linktalk.application.service.chatroom;

import com.tony.linktalk.adapter.in.web.dto.response.chat.room.ChatRoomResponseDto;
import com.tony.linktalk.application.command.chat.room.CreateChatRoomCommand;
import com.tony.linktalk.application.port.in.chat.room.CreateChatRoomUseCase;
import com.tony.linktalk.application.port.out.chatroom.CreateChatRoomPort;
import com.tony.linktalk.domain.ChatRoom;
import com.tony.linktalk.mapper.ChatRoomMapper;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class CreateChatRoomService implements CreateChatRoomUseCase {

    private final CreateChatRoomPort createChatRoomPort;
    private final ChatRoomMapper chatRoomMapper;


    /**
     * @param command CreateChatRoomCommand
     * @return ChatRoomResponseDto
     * @apiNote 1:1 DM 채팅방을 생성한다.
     */
    @Override
    public ChatRoomResponseDto createDmChatRoom(CreateChatRoomCommand command) {
        ChatRoom chatRoom = chatRoomMapper.commandToDomain(command);

//         todo: security에서 id를 뽑아내서 추가
//        SecurityUtil.getMemberId();
//        chatRoom.changeCreatorId(1L);

        ChatRoom savedChatRoom = createChatRoomPort.createDmChatRoom(chatRoom);
        return chatRoomMapper.domainToResponseDto(savedChatRoom);
    }

}
