package com.tony.linktalk.mapper;

import com.tony.linktalk.adapter.in.web.dto.response.chat.message.ChatMessageResponseDto;
import com.tony.linktalk.adapter.out.persistence.entity.chat.ChatMessageEntity;
import com.tony.linktalk.application.command.chat.message.CreateChatMessageCommand;
import com.tony.linktalk.domain.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ChatRoomMapper.class, MemberMapper.class}
)
public interface ChatMessageMapper {

    @Mapping(target = "chatRoom.chatRoomId", source = "chatRoomId")
    @Mapping(target = "sender.id", source = "senderId")
    ChatMessage commandToDomain(CreateChatMessageCommand createChatMessageCommand);

    @Mapping(target = "chatRoomId", source = "chatRoom.chatRoomId")
    @Mapping(target = "senderId", source = "sender.id")
    ChatMessageResponseDto domainToResponseDto(ChatMessage chatMessage);

    List<ChatMessageResponseDto> domainsToResponseDtos(List<ChatMessage> chatMessages);

    ChatMessage entityToDomain(ChatMessageEntity chatMessageEntity);

    List<ChatMessage> entitiesToDomains(List<ChatMessageEntity> chatMessageEntities);

    @Mapping(target = "chatRoomEntity", source = "chatRoom")
    @Mapping(target = "memberEntity", source = "sender")
    ChatMessageEntity domainToEntity(ChatMessage chatMessage);

}
