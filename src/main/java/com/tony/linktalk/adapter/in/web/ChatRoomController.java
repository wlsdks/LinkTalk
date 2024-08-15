package com.tony.linktalk.adapter.in.web;

import com.tony.linktalk.adapter.in.web.dto.request.RequestChatRoomDto;
import com.tony.linktalk.adapter.in.web.dto.response.ResponseChatRoomDto;
import com.tony.linktalk.application.command.CreateChatRoomCommand;
import com.tony.linktalk.application.command.FindChatRoomCommand;
import com.tony.linktalk.application.port.in.chatroom.CreateChatRoomUseCase;
import com.tony.linktalk.application.port.in.chatroom.FindChatRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/chat")
@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final FindChatRoomUseCase findChatRoomUseCase;
    private final CreateChatRoomUseCase createChatRoomUseCase;


    /**
     * @return ResponseChatRoomDto
     * @apiNote 1:1 DM 채팅방을 생성하는 API
     */
    @PostMapping("/room/dm/create")
    public ResponseChatRoomDto createDmChatRoom(
            @RequestBody RequestChatRoomDto createChatRoomDto
    ) {
        CreateChatRoomCommand command = CreateChatRoomCommand.of(createChatRoomDto);
        return createChatRoomUseCase.createDmChatRoom(command);
    }


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
     * @apiNote 내 채팅방 목록을 조회하는 API
     * todo: 이건 시큐리티로 id를 뽑아내서 처리해야할지 아니면 클라이언트한테 id를 받아야할지 고민해보자.
     */
    @GetMapping("/rooms/member/{memberId}")
    public List<ResponseChatRoomDto> getChatRoomsByMemberId(
            @PathVariable Long memberId
    ) {
        FindChatRoomCommand command = FindChatRoomCommand.of(memberId);
        return findChatRoomUseCase.findChatRoomsByMemberId(command);
    }

}
