package com.tony.linktalk.application.command.chat.message;

import com.tony.linktalk.adapter.in.web.dto.response.chat.message.ChatMessageResponseDto;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class CreateChatMessageCommand {

    private Long chatRoomId;
    private Long senderId;
    private String content;

    // factory method
    public static CreateChatMessageCommand of(ChatMessageResponseDto chatMessageResponseDto) {
        return CreateChatMessageCommand.builder()
                .chatRoomId(chatMessageResponseDto.getChatRoomId())
                .senderId(chatMessageResponseDto.getSenderId())
                .content(chatMessageResponseDto.getContent())
                .build();
    }

}
