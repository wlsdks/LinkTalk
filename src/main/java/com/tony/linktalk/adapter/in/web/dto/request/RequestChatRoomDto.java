package com.tony.linktalk.adapter.in.web.dto.request;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Setter
@Getter
public class RequestChatRoomDto {

    private String name;
    private Long senderId; // DM 상대방 Member ID

    // factory method
    public static RequestChatRoomDto of(String name, Long senderId) {
        return RequestChatRoomDto.builder()
                .name(name)
                .senderId(senderId)
                .build();
    }

}
