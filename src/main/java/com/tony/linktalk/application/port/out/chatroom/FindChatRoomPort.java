package com.tony.linktalk.application.port.out.chatroom;

import com.tony.linktalk.domain.ChatRoom;

import java.util.List;

public interface FindChatRoomPort {

    List<ChatRoom> findChatRoomsByMemberId(Long memberId);

    List<ChatRoom> findAllChatRooms();

}
