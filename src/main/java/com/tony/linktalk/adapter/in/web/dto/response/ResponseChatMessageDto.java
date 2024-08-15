package com.tony.linktalk.adapter.in.web.dto.response;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ResponseChatMessageDto {

    private Long chatRoomId;
    private Long senderId;
    private String content;

    // factory method
    public static ResponseChatMessageDto of(Long chatRoomId, Long senderId, String content) {
        return new ResponseChatMessageDto(chatRoomId, senderId, content);
    }

}
