package com.tony.linktalk.application.service.chat;

import com.tony.linktalk.adapter.in.web.dto.response.chat.message.ChatMessageResponseDto;
import com.tony.linktalk.adapter.out.event.ChatMessageStatusChangeEvent;
import com.tony.linktalk.application.command.chat.message.CreateChatMessageCommand;
import com.tony.linktalk.application.port.in.chat.message.CreateChatMessageUseCase;
import com.tony.linktalk.application.port.out.chat.CreateChatMessagePort;
import com.tony.linktalk.domain.ChatMessage;
import com.tony.linktalk.mapper.ChatMessageMapper;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@UseCase
public class CreateChatMessageService implements CreateChatMessageUseCase {

    private final CreateChatMessagePort createChatMessagePort;
    private final ChatMessageMapper chatMessageMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * @param createChatMessageCommand CreateChatMessageCommand
     * @apiNote 채팅 메시지를 처리하는 비즈니스 로직
     */
    @Transactional
    @Override
    public void createChatMessage(CreateChatMessageCommand createChatMessageCommand) {
        // 1. command를 domain으로 변환
        ChatMessage chatMessage = chatMessageMapper.commandToDomain(createChatMessageCommand);

        // 2. 메시지를 저장
        ChatMessage savedChatMessage = createChatMessagePort.createChatMessage(chatMessage);

        // 3. 이벤트를 발행하여 메시지 상태를 변경
        applicationEventPublisher.publishEvent(ChatMessageStatusChangeEvent.of(savedChatMessage));
    }

}
