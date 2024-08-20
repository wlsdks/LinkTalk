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
    private String content;

    // factory method
    public static ChatMessageResponseDto of(Long chatRoomId, Long senderId, String content) {
        return new ChatMessageResponseDto(chatRoomId, senderId, content);
    }

    // factory method
    public static ChatMessageResponseDto of(ChatWebSocketMessage chatWebSocketMessage) {
        return new ChatMessageResponseDto(chatWebSocketMessage.getChatRoomId(), chatWebSocketMessage.getSenderId(), chatWebSocketMessage.getContent());
    }

}
