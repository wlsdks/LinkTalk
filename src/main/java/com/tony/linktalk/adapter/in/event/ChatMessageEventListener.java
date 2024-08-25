package com.tony.linktalk.adapter.in.event;

import com.tony.linktalk.adapter.out.event.ChatMessageStatusChangeEvent;
import com.tony.linktalk.application.command.chat.message.UpdateChatMessageStatusCommand;
import com.tony.linktalk.application.port.in.chat.message.UpdateChatMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 채팅 메시지 이벤트 리스너
 */
@RequiredArgsConstructor
@Component
public class ChatMessageEventListener {

    private final UpdateChatMessageUseCase updateChatMessageUseCase;

    /**
     * @param event ChatMessageStatusChangeEvent
     * @apiNote 채팅 메시지 상태 변경 이벤트를 처리하는 메서드
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleChatMessageStatusChangeEvent(ChatMessageStatusChangeEvent event) {
        // 1. 이벤트를 받아서 처리
        System.out.println("ChatMessageStatusChangeEvent: " + event.getChatMessageId());

        // 2. command 객체 생성
        UpdateChatMessageStatusCommand command = UpdateChatMessageStatusCommand.of(event.getChatMessageId(), event.getChatMessageStatus());

        // 3. 채팅 메시지 상태 변경
        updateChatMessageUseCase.updateChatMessageStatus(command);
    }

}
