package com.tony.linktalk.application.service.chat;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatMessageDto;
import com.tony.linktalk.application.command.FindChatMessageCommand;
import com.tony.linktalk.application.port.in.chat.FindChatMessageUseCase;
import com.tony.linktalk.application.port.out.chat.FindChatMessagePort;
import com.tony.linktalk.domain.ChatMessage;
import com.tony.linktalk.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FindChatMessageService implements FindChatMessageUseCase {

    private final FindChatMessagePort findChatMessagePort;
    private final ChatMessageMapper chatMessageMapper;

    /**
     * @param command 커맨드 객체
     * @return List<ResponseChatMessageDto>
     * @apiNote 채팅방의 메시지 목록을 조회하는 API
     */
    @Override
    public List<ResponseChatMessageDto> findMessagesByRoomId(FindChatMessageCommand command) {
        List<ChatMessage> chatMessages = findChatMessagePort.findAllMessagesByRoomId(command.getRoomId());
        return chatMessageMapper.domainsToResponseDtos(chatMessages);
    }

}
