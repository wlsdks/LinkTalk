package com.tony.linktalk.adapter.in.web.dto.response.chat.room;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class ChatRoomResponseDto {

    private Long id;
    private String name;

    // factory method
    public static ChatRoomResponseDto of(Long id, String name) {
        return ChatRoomResponseDto.builder()
                .id(id)
                .name(name)
                .build();
    }

}
