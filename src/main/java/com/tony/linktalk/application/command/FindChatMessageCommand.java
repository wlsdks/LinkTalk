package com.tony.linktalk.application.command;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class FindChatMessageCommand {

    private Long roomId;

    // factory method
    public static FindChatMessageCommand of(Long roomId) {
        return FindChatMessageCommand.builder()
                .roomId(roomId)
                .build();
    }

}
