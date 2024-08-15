package com.tony.linktalk.adapter.in.web;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatMessageDto;
import com.tony.linktalk.application.command.FindChatMessageCommand;
import com.tony.linktalk.application.port.in.chat.FindChatMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<ResponseChatMessageDto> getMessagesFromRoom(
            @PathVariable Long roomId
    ) {
        FindChatMessageCommand command = FindChatMessageCommand.of(roomId);
        return findChatMessageUseCase.findMessagesByRoomId(command);
    }

}
