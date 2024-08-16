package com.tony.linktalk.application.command.chat.room;

import com.tony.linktalk.adapter.in.web.dto.request.chat.room.ChatRoomRequestDto;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class CreateChatRoomCommand {

    private String name;
    private Long senderId;

    // factory method
    public static CreateChatRoomCommand of(ChatRoomRequestDto createChatRoomDto) {
        return CreateChatRoomCommand.builder()
                .name(createChatRoomDto.getName())
                .senderId(createChatRoomDto.getSenderId())
                .build();
    }

}
