package com.tony.linktalk.application.command.post;

import com.tony.linktalk.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class FindPostCommand {

    private Long id;
    private Member member;

    // factory method
    public static FindPostCommand of(Long postId) {
        return FindPostCommand.builder()
                .id(postId)
                .build();
    }

}
