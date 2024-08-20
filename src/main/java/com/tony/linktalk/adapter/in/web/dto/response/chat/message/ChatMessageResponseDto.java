package com.tony.linktalk.adapter.in.web.dto.response.chat.message;

import com.tony.linktalk.config.websocket.dto.ChatWebSocketMessage;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ChatMessageResponseDto {

    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;
    private String content;

    // factory method
    public static ChatMessageResponseDto of(ChatWebSocketMessage chatWebSocketMessage) {
        return ChatMessageResponseDto.builder()
                .chatRoomId(chatWebSocketMessage.getChatRoomId())
                .senderId(chatWebSocketMessage.getSenderId())
                .receiverId(chatWebSocketMessage.getReceiverId())
                .content(chatWebSocketMessage.getContent())
                .build();
    }

}
