package com.tony.linktalk.application.command.post;

import com.tony.linktalk.domain.Member;
import com.tony.linktalk.exception.ErrorCode;
import com.tony.linktalk.exception.LinkTalkException;
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
        if (postId == null) {
            throw new LinkTalkException(ErrorCode.POST_ID_IS_NULL);
        }

        return FindPostCommand.builder()
                .id(postId)
                .build();
    }

}
