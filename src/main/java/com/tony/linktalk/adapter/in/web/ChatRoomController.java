package com.tony.linktalk.adapter.in.web;

import com.tony.linktalk.adapter.in.web.dto.request.chat.room.ChatRoomRequestDto;
import com.tony.linktalk.adapter.in.web.dto.response.chat.room.ChatRoomResponseDto;
import com.tony.linktalk.application.command.chat.room.CreateChatRoomCommand;
import com.tony.linktalk.application.command.chat.room.FindChatRoomCommand;
import com.tony.linktalk.application.port.in.chat.room.CreateChatRoomUseCase;
import com.tony.linktalk.application.port.in.chat.room.FindChatRoomUseCase;
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
     * @return ChatRoomResponseDto
     * @apiNote 1:1 DM 채팅방을 생성하는 API
     */
    @PostMapping("/room/dm/create")
    public ChatRoomResponseDto createDmChatRoom(
            @RequestBody ChatRoomRequestDto createChatRoomDto
    ) {
        CreateChatRoomCommand command = CreateChatRoomCommand.of(createChatRoomDto);
        return createChatRoomUseCase.createDmChatRoom(command);
    }


    /**
     * @return List<ChatRoomResponseDto>
     * @apiNote 채팅방 목록을 조회하는 API
     */
    @GetMapping("/rooms")
    public List<ChatRoomResponseDto> getAllChatRooms() {
        return findChatRoomUseCase.findAllChatRooms();
    }


    /**
     * @param memberId Long
     * @return List<ChatRoomResponseDto>
     * @apiNote 내 채팅방 목록을 조회하는 API
     * todo: 이건 시큐리티로 id를 뽑아내서 처리해야할지 아니면 클라이언트한테 id를 받아야할지 고민해보자.
     */
    @GetMapping("/rooms/member/{memberId}")
    public List<ChatRoomResponseDto> getChatRoomsByMemberId(
            @PathVariable Long memberId
    ) {
        FindChatRoomCommand command = FindChatRoomCommand.of(memberId);
        return findChatRoomUseCase.findChatRoomsByMemberId(command);
    }

}
