package com.tony.linktalk.application.port.in.chat;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatMessageDto;
import com.tony.linktalk.application.command.FindChatMessageCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindChatMessageUseCase {

    Page<ResponseChatMessageDto> findMessagesByRoomId(FindChatMessageCommand command, Pageable pageable);

}
