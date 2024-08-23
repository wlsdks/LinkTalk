package com.tony.linktalk.config.websocket.dto;

import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageType;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ChatWebSocketMessage {

    private ChatMessageType chatMessageType;
    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;
    private String content; // 메시지 내용 또는 파일 URL

    // factory method
    public static ChatWebSocketMessage of(ChatMessageType chatMessageType, long chatRoomId, long senderId, String testMessageContent) {
        return ChatWebSocketMessage.builder()
                .chatMessageType(chatMessageType)
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .content(testMessageContent)
                .build();
    }

    /**
     * @param chatRoomId 채팅방 ID
     * @apiNote 채팅방 ID를 변경한다.
     */
    public void changeChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }


    /**
     * @param userId 수신자 ID
     * @apiNote 채팅 메시지의 수신자 ID를 변경한다.
     */
    public void changeReceiverId(Long userId) {
        this.receiverId = userId;
    }

}
