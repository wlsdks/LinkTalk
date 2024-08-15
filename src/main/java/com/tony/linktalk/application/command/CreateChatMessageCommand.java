package com.tony.linktalk.application.command;

import com.tony.linktalk.adapter.in.web.dto.ChatMessageDto;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CreateChatMessageCommand {

    private Long chatRoomId;
    private Long senderId;
    private String content;

    // factory method
    public static CreateChatMessageCommand of(ChatMessageDto chatMessageDto) {
        return CreateChatMessageCommand.builder()
                .chatRoomId(chatMessageDto.getChatRoomId())
                .senderId(chatMessageDto.getSenderId())
                .content(chatMessageDto.getContent())
                .build();
    }

}
