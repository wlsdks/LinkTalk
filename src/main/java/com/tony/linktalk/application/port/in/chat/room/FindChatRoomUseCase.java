package com.tony.linktalk.application.port.in.chat.room;

import com.tony.linktalk.adapter.in.web.dto.response.chat.room.ChatRoomResponseDto;
import com.tony.linktalk.application.command.chat.room.FindChatRoomCommand;

import java.util.List;

public interface FindChatRoomUseCase {

    List<ChatRoomResponseDto> findAllChatRooms();

    List<ChatRoomResponseDto> findChatRoomsByMemberId(FindChatRoomCommand command);

}
