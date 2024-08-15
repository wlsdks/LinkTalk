package com.tony.linktalk.application.port.out.chatroom;

import com.tony.linktalk.domain.ChatRoom;

public interface CreateChatRoomPort {

    ChatRoom createDmChatRoom(ChatRoom chatRoom);

}
