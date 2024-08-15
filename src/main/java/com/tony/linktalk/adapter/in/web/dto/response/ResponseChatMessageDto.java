package com.tony.linktalk.adapter.in.web.dto.response;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ResponseChatMessageDto {

    private Long chatRoomId;
    private Long senderId;
    private String content;
    private String createdAt;

    // factory method
    public static ResponseChatMessageDto of(Long chatRoomId, Long senderId, String content, String createdAt) {
        return ResponseChatMessageDto.builder()
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .content(content)
                .createdAt(createdAt)
                .build();
    }

}
