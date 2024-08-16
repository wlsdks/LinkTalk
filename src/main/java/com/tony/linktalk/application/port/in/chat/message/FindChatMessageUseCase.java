package com.tony.linktalk.application.port.in.chat.message;

import com.tony.linktalk.adapter.in.web.dto.response.chat.message.ChatMessageResponseDto;
import com.tony.linktalk.application.command.chat.message.FindChatMessageCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindChatMessageUseCase {

    Page<ChatMessageResponseDto> findMessagesByRoomId(FindChatMessageCommand command, Pageable pageable);

}
