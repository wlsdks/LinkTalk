package com.tony.linktalk.application.port.in.chatroom;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatRoomDto;
import com.tony.linktalk.application.command.CreateChatRoomCommand;

public interface CreateChatRoomUseCase {

    ResponseChatRoomDto createDmChatRoom(CreateChatRoomCommand command);

}
