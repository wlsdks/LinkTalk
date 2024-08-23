package com.tony.linktalk.application.service.chat;

import com.tony.linktalk.application.command.chat.message.UpdateChatMessageStatusCommand;
import com.tony.linktalk.application.port.in.chat.message.UpdateChatMessageUseCase;
import com.tony.linktalk.application.port.out.chat.FindChatMessagePort;
import com.tony.linktalk.application.port.out.chat.UpdateChatMessagePort;
import com.tony.linktalk.domain.ChatMessage;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@UseCase
public class UpdateChatMessageService implements UpdateChatMessageUseCase {

    private final FindChatMessagePort findChatMessagePort;
    private final UpdateChatMessagePort updateChatMessagePort;

    /**
     * @param command UpdateChatMessageStatusCommand
     * @apiNote 채팅 메시지 상태 변경
     */
    @Transactional
    @Override
    public void updateChatMessageStatus(UpdateChatMessageStatusCommand command) {
        ChatMessage findChatMessage = findChatMessagePort.findChatMessageById(command.getChatMessageId());
        findChatMessage.changeChatMessageStatus(command.getChatMessageStatus());
        updateChatMessagePort.updateChatMessageStatus(findChatMessage);
    }

}
