package com.tony.linktalk.application.port.in.chat.message;

import com.tony.linktalk.application.command.chat.message.UpdateChatMessageStatusCommand;

public interface UpdateChatMessageUseCase {

    void updateChatMessageStatus(UpdateChatMessageStatusCommand command);

}
