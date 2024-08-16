package com.tony.linktalk.application.port.out.chat;

import com.tony.linktalk.domain.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindChatMessagePort {

    Page<ChatMessage> findAllMessagesByRoomId(Long roomId, Pageable pageable);

}
