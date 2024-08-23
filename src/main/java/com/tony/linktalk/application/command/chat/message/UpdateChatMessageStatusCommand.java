package com.tony.linktalk.application.command.chat.message;

import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageStatus;
import com.tony.linktalk.exception.ErrorCode;
import com.tony.linktalk.exception.LinkTalkException;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class UpdateChatMessageStatusCommand {

    private Long chatMessageId;
    private ChatMessageStatus chatMessageStatus;

    // factory method
    public static UpdateChatMessageStatusCommand of(Long chatMessageId, ChatMessageStatus chatMessageStatus) {
        if (chatMessageId == null) {
            throw new LinkTalkException(ErrorCode.CHAT_MESSAGE_ID_NOT_FOUND);
        }
        if (chatMessageStatus == null) {
            throw new LinkTalkException(ErrorCode.CHAT_MESSAGE_STATUS_NOT_FOUND);
        }
        return UpdateChatMessageStatusCommand.builder()
                .chatMessageId(chatMessageId)
                .chatMessageStatus(chatMessageStatus)
                .build();
    }

}
