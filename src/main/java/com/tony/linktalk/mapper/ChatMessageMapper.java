package com.tony.linktalk.mapper;

import com.tony.linktalk.adapter.in.web.dto.ChatMessageDto;
import com.tony.linktalk.application.command.CreateChatMessageCommand;
import com.tony.linktalk.domain.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ChatMessageMapper {

    ChatMessage commandToDomain(CreateChatMessageCommand createChatMessageCommand);

    ChatMessageDto domainToDto(ChatMessage chatMessage);

}
