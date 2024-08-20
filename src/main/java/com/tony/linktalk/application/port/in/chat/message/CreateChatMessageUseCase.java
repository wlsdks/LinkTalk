package com.tony.linktalk.application.port.in.chat.message;

import com.tony.linktalk.application.command.chat.message.CreateChatMessageCommand;

public interface CreateChatMessageUseCase {

    void createChatMessage(CreateChatMessageCommand createChatMessageCommand);

}
