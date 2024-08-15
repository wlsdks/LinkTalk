package com.tony.linktalk.domain;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ChatMessage {

    private Long chatMessageId;
    private ChatRoom chatRoom;
    private Member sender;
    private String content;
    private LocalDateTime createdAt;

    // factory method
    public static ChatMessage of(ChatRoom chatRoom, Member sender, String content, LocalDateTime createdAt) {
        return new ChatMessage(null, chatRoom, sender, content, createdAt);
    }

}


