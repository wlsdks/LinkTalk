package com.tony.linktalk.application.port.in.chat.room;

import com.tony.linktalk.adapter.in.web.dto.response.chat.room.ChatRoomResponseDto;
import com.tony.linktalk.application.command.chat.room.CreateChatRoomCommand;

public interface CreateChatRoomUseCase {

    ChatRoomResponseDto createDmChatRoom(CreateChatRoomCommand command);

}
