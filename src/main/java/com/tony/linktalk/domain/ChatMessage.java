package com.tony.linktalk.domain;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class ChatMessage {

    private Long id;
    private ChatRoom chatRoom;
    private Member sender;
    private String content;
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

}


