package com.tony.linktalk.adapter.in.web.dto;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class ChatMessageDto {

    private Long chatRoomId;
    private Long senderId;
    private String content;

}
