package com.tony.linktalk.adapter.in.web.dto.request.chat.message;

import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageStatus;
import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageType;
import com.tony.linktalk.config.websocket.dto.ChatWebSocketMessage;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ChatMessageRequestDto {

    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private ChatMessageType chatMessageType;
    private ChatMessageStatus chatMessageStatus;

    // factory method
    public static ChatMessageRequestDto of(ChatWebSocketMessage chatWebSocketMessage) {
        return ChatMessageRequestDto.builder()
                .chatRoomId(chatWebSocketMessage.getChatRoomId())
                .senderId(chatWebSocketMessage.getSenderId())
                .receiverId(chatWebSocketMessage.getReceiverId())
                .content(chatWebSocketMessage.getContent())
                .chatMessageType(chatWebSocketMessage.getChatMessageType())
                .chatMessageStatus(ChatMessageStatus.SENT)
                .build();
    }

}
