package com.tony.linktalk.mapper;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatMessageDto;
import com.tony.linktalk.adapter.out.persistence.entity.ChatMessageEntity;
import com.tony.linktalk.application.command.CreateChatMessageCommand;
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

    ChatMessage commandToDomain(CreateChatMessageCommand createChatMessageCommand);

    @Mapping(target = "chatRoomId", source = "chatRoom.chatRoomId")
    @Mapping(target = "senderId", source = "sender.memberId")
    ResponseChatMessageDto domainToResponseDto(ChatMessage chatMessage);

    List<ResponseChatMessageDto> domainsToResponseDtos(List<ChatMessage> chatMessages);

    ChatMessage entityToDomain(ChatMessageEntity chatMessageEntity);

    List<ChatMessage> entitiesToDomains(List<ChatMessageEntity> chatMessageEntities);

    @Mapping(target = "chatRoomEntity", source = "chatRoom")
    @Mapping(target = "senderEntity", source = "sender")
    ChatMessageEntity domainToEntity(ChatMessage chatMessage);

}
