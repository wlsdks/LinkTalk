package com.tony.linktalk.application.service.chat;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatMessageDto;
import com.tony.linktalk.application.command.FindChatMessageCommand;
import com.tony.linktalk.application.port.in.chat.FindChatMessageUseCase;
import com.tony.linktalk.application.port.out.chat.FindChatMessagePort;
import com.tony.linktalk.domain.ChatMessage;
import com.tony.linktalk.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<ResponseChatMessageDto> findMessagesByRoomId(FindChatMessageCommand command, Pageable pageable) {

        // todo: 요청 보내는 jwt 토큰에서 id를 뽑아내고 시큐리티 id랑 비교해서 처리해야함 (필요한가?)
        Page<ChatMessage> chatMessages = findChatMessagePort.findAllMessagesByRoomId(command.getRoomId(), pageable);
        return chatMessages.map(chatMessageMapper::domainToResponseDto);
    }

}
