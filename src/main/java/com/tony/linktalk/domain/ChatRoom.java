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
    private Long createdBy; // 생성자 MemberEntity ID

    // factory method
    public static ChatRoom of(String name, Long createdBy) {
        return ChatRoom.builder()
                .name(name)
                .createdBy(createdBy)
                .build();
    }

}
