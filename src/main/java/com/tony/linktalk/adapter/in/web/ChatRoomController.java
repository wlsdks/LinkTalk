package com.tony.linktalk.adapter.in.web;

import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatRoomDto;
import com.tony.linktalk.application.command.FindChatRoomCommand;
import com.tony.linktalk.application.port.in.chatroom.FindChatRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/chat")
@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final FindChatRoomUseCase findChatRoomUseCase;

    /**
     * @return List<ResponseChatRoomDto>
     * @apiNote 채팅방 목록을 조회하는 API
     */
    @GetMapping("/rooms")
    public List<ResponseChatRoomDto> getAllChatRooms() {
        return findChatRoomUseCase.findAllChatRooms();
    }


    /**
     * @param memberId Long
     * @return List<ResponseChatRoomDto>
     * @apiNote 특정 회원이 속한 채팅방 목록을 조회하는 API
     */
    @GetMapping("/rooms/member/{memberId}")
    public List<ResponseChatRoomDto> getChatRoomsByMemberId(
            @PathVariable Long memberId
    ) {
        FindChatRoomCommand command = FindChatRoomCommand.of(memberId);
        return findChatRoomUseCase.findChatRoomsByMemberId(command);
    }

}
