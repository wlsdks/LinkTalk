package com.tony.linktalk.domain;

import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageStatus;
import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Builder
public class ChatMessage {

    private Long chatMessageId;
    private ChatRoom chatRoom;
    private Member sender;
    private String content;
    private ChatMessageStatus chatMessageStatus;
    private ChatMessageType chatMessageType;
    private LocalDateTime createdAt;

    // factory method
    public static ChatMessage of(ChatRoom chatRoom, Member sender, String content, LocalDateTime createdAt) {
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(content)
                .createdAt(createdAt)
                .build();
    }

    /**
     * @param chatMessageStatus ChatMessageStatus
     * @apiNote 채팅 메시지 상태 변경
     */
    public void changeChatMessageStatus(ChatMessageStatus chatMessageStatus) {
        this.chatMessageStatus = chatMessageStatus;
    }

}


