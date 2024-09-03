package com.tony.linktalk.application.command.post;

import com.tony.linktalk.adapter.out.persistence.entity.constant.post.VisibilityCode;
import com.tony.linktalk.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 게시글 Command (CQRS)
 */
public class PostCommand {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    public static class Create {

        private String title;
        private VisibilityCode visibilityCode;
        private String status;
        private Boolean isPinned;
        private String content;  // 게시글 내용
        private Boolean allowComments;
        private Boolean isShareable;

    }


    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    public static class Find {

        private Long id;
        private Member member;

        // factory method
        public static Find of(Long postId) {
            return Find.builder()
                    .id(postId)
                    .build();
        }

    }


    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    public static class Update {

        private Long id;
        private String title;
        private VisibilityCode visibilityCode;
        private String status;
        private Boolean isPinned;
        private String content;  // 게시글 내용
        private Boolean allowComments;
        private Boolean isShareable;

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    public static class Delete {

        private Long id;

    }

}