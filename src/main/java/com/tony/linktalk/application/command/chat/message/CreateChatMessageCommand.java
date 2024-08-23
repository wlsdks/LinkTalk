package com.tony.linktalk.application.command.chat.message;

import com.tony.linktalk.adapter.in.web.dto.request.chat.message.ChatMessageRequestDto;
import com.tony.linktalk.adapter.in.web.dto.response.chat.message.ChatMessageResponseDto;
import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageStatus;
import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageType;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class CreateChatMessageCommand {

    private Long chatRoomId;
    private Long senderId;
    private String content;
    private ChatMessageStatus chatMessageStatus;
    private ChatMessageType chatMessageType;

    // factory method
    public static CreateChatMessageCommand of(ChatMessageRequestDto chatMessageResponseDto) {
        return CreateChatMessageCommand.builder()
                .chatRoomId(chatMessageResponseDto.getChatRoomId())
                .senderId(chatMessageResponseDto.getSenderId())
                .content(chatMessageResponseDto.getContent())
                .chatMessageType(chatMessageResponseDto.getChatMessageType())
                .chatMessageStatus(chatMessageResponseDto.getChatMessageStatus())
                .build();
    }

}
