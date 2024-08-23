package com.tony.linktalk.adapter.out.event;

import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageStatus;
import com.tony.linktalk.domain.ChatMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageStatusChangeEvent {

    private final Long chatMessageId;
    private final ChatMessageStatus chatMessageStatus;

    public static ChatMessageStatusChangeEvent of(ChatMessage chatMessage) {
        return new ChatMessageStatusChangeEvent(chatMessage.getChatMessageId(), chatMessage.getChatMessageStatus());
    }

}
