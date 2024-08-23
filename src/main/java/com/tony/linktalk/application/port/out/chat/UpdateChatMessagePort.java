package com.tony.linktalk.application.port.out.chat;

import com.tony.linktalk.domain.ChatMessage;

public interface UpdateChatMessagePort {

    void updateChatMessageStatus(ChatMessage chatMessage);

}
