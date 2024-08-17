package com.tony.linktalk.application.service.chat;

import com.tony.linktalk.adapter.in.web.dto.response.chat.message.ChatMessageResponseDto;
import com.tony.linktalk.application.command.chat.message.CreateChatMessageCommand;
import com.tony.linktalk.application.port.out.chat.CreateChatMessagePort;
import com.tony.linktalk.domain.ChatMessage;
import com.tony.linktalk.mapper.ChatMessageMapper;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class CreateChatMessageService {

    private final CreateChatMessagePort createChatMessagePort;
    private final ChatMessageMapper chatMessageMapper;


    /**
     * @param createChatMessageCommand CreateChatMessageCommand
     * @apiNote 채팅 메시지를 처리하는 비즈니스 로직
     */
    public void processChatMessage(CreateChatMessageCommand createChatMessageCommand) {
        // 1. command를 domain으로 변환
        ChatMessage chatMessage = chatMessageMapper.commandToDomain(createChatMessageCommand);

        // 2. 메시지를 저장
        ChatMessage savedChatMessage = createChatMessagePort.createChatMessage(chatMessage);

        // 3. 저장된 메시지를 DTO로 변환
        ChatMessageResponseDto responseDto = chatMessageMapper.domainToResponseDto(savedChatMessage);
    }

}
