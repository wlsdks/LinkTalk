package com.tony.linktalk.application.command;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatMessageDto;
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
    public static CreateChatMessageCommand of(ResponseChatMessageDto responseChatMessageDto) {
        return CreateChatMessageCommand.builder()
                .chatRoomId(responseChatMessageDto.getChatRoomId())
                .senderId(responseChatMessageDto.getSenderId())
                .content(responseChatMessageDto.getContent())
                .build();
    }

}
