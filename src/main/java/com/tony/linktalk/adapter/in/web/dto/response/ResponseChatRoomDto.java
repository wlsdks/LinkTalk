package com.tony.linktalk.adapter.in.web.dto.response;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class ResponseChatRoomDto {

    private Long id;
    private String name;

    // factory method
    public static ResponseChatRoomDto of(Long id, String name) {
        return ResponseChatRoomDto.builder()
                .id(id)
                .name(name)
                .build();
    }

}
