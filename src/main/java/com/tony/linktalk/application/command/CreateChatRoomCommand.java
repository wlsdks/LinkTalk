package com.tony.linktalk.application.command;

import com.tony.linktalk.adapter.in.web.dto.request.RequestChatRoomDto;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class CreateChatRoomCommand {

    private String name;
    private Long senderId;

    // factory method
    public static CreateChatRoomCommand of(RequestChatRoomDto createChatRoomDto) {
        return CreateChatRoomCommand.builder()
                .name(createChatRoomDto.getName())
                .senderId(createChatRoomDto.getSenderId())
                .build();
    }

}
