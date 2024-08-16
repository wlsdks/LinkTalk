package com.tony.linktalk.application.command.chat.room;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class FindChatRoomCommand {

    private Long memberId;

    // factory method
    public static FindChatRoomCommand of(Long memberId) {
        return FindChatRoomCommand.builder()
                .memberId(memberId)
                .build();
    }

}
