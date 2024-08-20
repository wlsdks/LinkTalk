package com.tony.linktalk.config.websocket.dto;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ChatWebSocketMessage {

    private String messageType; // "TEXT" 또는 "FILE"
    private Long chatRoomId;
    private Long senderId;
    private String content; // 메시지 내용 또는 파일 URL

    /**
     * @param chatRoomId 채팅방 ID
     * @apiNote 채팅방 ID를 변경한다.
     */
    public void changeChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

}
