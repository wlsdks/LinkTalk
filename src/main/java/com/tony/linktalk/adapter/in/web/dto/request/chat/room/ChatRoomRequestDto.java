package com.tony.linktalk.adapter.in.web.dto.request.chat.room;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ChatRoomRequestDto {

    private String name;
    private Long senderId; // DM 상대방 Member ID

    // factory method
    public static ChatRoomRequestDto of(String name, Long senderId) {
        return ChatRoomRequestDto.builder()
                .name(name)
                .senderId(senderId)
                .build();
    }

}
