package com.tony.linktalk.application.command.chat.room;

import com.tony.linktalk.adapter.in.web.dto.request.chat.room.ChatRoomRequestDto;
import com.tony.linktalk.adapter.out.persistence.entity.constant.room.RoomType;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class CreateChatRoomCommand {

    private String name;
    private Long invitedMemberId;
    private RoomType roomType;

    // factory method
    public static CreateChatRoomCommand of(ChatRoomRequestDto createChatRoomDto) {
        return CreateChatRoomCommand.builder()
                .name(createChatRoomDto.getName())
                .invitedMemberId(createChatRoomDto.getInvitedMemberId())
                .roomType(createChatRoomDto.getRoomType())
                .build();
    }

}
