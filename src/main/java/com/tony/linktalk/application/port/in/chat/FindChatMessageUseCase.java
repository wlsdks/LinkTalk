package com.tony.linktalk.application.port.in.chat;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatMessageDto;
import com.tony.linktalk.application.command.FindChatMessageCommand;

import java.util.List;

public interface FindChatMessageUseCase {

    List<ResponseChatMessageDto> findMessagesByRoomId(FindChatMessageCommand command);

}
