package com.tony.linktalk.adapter.in.web.dto.response.chat.message;

import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageStatus;
import com.tony.linktalk.adapter.out.persistence.entity.constant.message.ChatMessageType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class ChatMessageResponseDto {

    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private ChatMessageType chatMessageType;
    private ChatMessageStatus chatMessageStatus;
    private LocalDateTime createdAt;

}
