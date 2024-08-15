package com.tony.linktalk.application.port.in.chatroom;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatRoomDto;
import com.tony.linktalk.application.command.FindChatRoomCommand;

import java.util.List;

public interface FindChatRoomUseCase {

    List<ResponseChatRoomDto> findAllChatRooms();

    List<ResponseChatRoomDto> findChatRoomsByMemberId(FindChatRoomCommand command);

}
