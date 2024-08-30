package com.tony.linktalk.adapter.in.web.dto.request.chat.message;

import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageStatus;
import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageType;
import com.tony.linktalk.config.websocket.dto.ChatWebSocketMessageDto;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder

@Getter
public class ChatMessageRequestDto {

    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private ChatMessageType chatMessageType;
    private ChatMessageStatus chatMessageStatus;

    // factory method
    public static ChatMessageRequestDto of(ChatWebSocketMessageDto chatWebSocketMessageDto) {
        return ChatMessageRequestDto.builder()
                .chatRoomId(chatWebSocketMessageDto.getChatRoomId())
                .senderId(chatWebSocketMessageDto.getSenderId())
                .receiverId(chatWebSocketMessageDto.getReceiverId())
                .content(chatWebSocketMessageDto.getContent())
                .chatMessageType(chatWebSocketMessageDto.getChatMessageType())
                .chatMessageStatus(ChatMessageStatus.SENT)
                .build();
    }

}
