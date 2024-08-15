package com.tony.linktalk.domain;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ChatRoom {

    private Long chatRoomId;
    private String name;
    private Long creatorId; // 생성자 Member ID


    // factory method
    public static ChatRoom of(String name, Long creatorId) {
        return ChatRoom.builder()
                .name(name)
                .creatorId(creatorId)
                .build();
    }

}
