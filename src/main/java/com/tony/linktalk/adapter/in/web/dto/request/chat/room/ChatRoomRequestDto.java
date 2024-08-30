package com.tony.linktalk.adapter.in.web.dto.request.chat.room;

import com.tony.linktalk.adapter.out.persistence.entity.constant.room.RoomType;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class ChatRoomRequestDto {

    private String name;
    private Long invitedMemberId;
    private RoomType roomType;

    // factory method
    public static ChatRoomRequestDto of(String name, Long senderId, RoomType roomType) {
        return ChatRoomRequestDto.builder()
                .name(name)
                .invitedMemberId(senderId)
                .roomType(roomType)
                .build();
    }

}
