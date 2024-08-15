package com.tony.linktalk.application.port.out.chat;

import com.tony.linktalk.domain.ChatMessage;

import java.util.List;

public interface FindChatMessagePort {

    List<ChatMessage> findAllMessagesByRoomId(Long roomId);

}
