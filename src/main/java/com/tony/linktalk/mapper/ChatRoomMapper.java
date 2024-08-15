package com.tony.linktalk.mapper;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatRoomDto;
import com.tony.linktalk.adapter.out.persistence.entity.ChatRoomEntity;
import com.tony.linktalk.domain.ChatRoom;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ChatRoomMapper {

    ChatRoom entityToDomain(ChatRoomEntity chatRoomEntity);

    List<ChatRoom> entitiesToDomains(List<ChatRoomEntity> chatRoomEntities);

    ResponseChatRoomDto domainToResponseDto(ChatRoom chatRoom);

    List<ResponseChatRoomDto> domainsToResponseDtos(List<ChatRoom> chatRooms);

}
