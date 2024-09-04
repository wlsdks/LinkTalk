package com.tony.linktalk.application.command.post;

import com.tony.linktalk.adapter.out.persistence.entity.constant.post.VisibilityCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CreatePostCommand {

    private String title;
    private VisibilityCode visibilityCode;
    private String status;
    private Boolean isPinned;
    private String content;  // 게시글 내용
    private Boolean allowComments;
    private Boolean isShareable;

}
