package com.tony.linktalk.adapter.in.web;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatMessageDto;
import com.tony.linktalk.application.command.FindChatMessageCommand;
import com.tony.linktalk.application.port.in.chat.FindChatMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 채팅 관련 API 컨트롤러
 */
@RequestMapping("/chat")
@RequiredArgsConstructor
@RestController
public class ChatMessageController {

    private final FindChatMessageUseCase findChatMessageUseCase;


    /**
     * @param roomId Long
     * @return List<ResponseChatMessageDto>
     * @apiNote 채팅방 안에 있는 모든 메시지들을 조회한다.
     */
    @GetMapping("/rooms/{roomId}/messages")
    public Page<ResponseChatMessageDto> getMessagesFromRoom(
            @PathVariable Long roomId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        FindChatMessageCommand command = FindChatMessageCommand.of(roomId);
        return findChatMessageUseCase.findMessagesByRoomId(command, pageable);
    }

}
