package com.tony.linktalk.application.command.post;

import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;
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

    // factory method
    public static CreatePostCommand of(PostResponseDto postResponseDto) {
        return CreatePostCommand.builder()
                .title(postResponseDto.getTitle())
                .visibilityCode(postResponseDto.getVisibilityCode())
                .status(postResponseDto.getStatus())
                .isPinned(postResponseDto.getIsPinned())
                .content(postResponseDto.getContent())
                .allowComments(postResponseDto.getAllowComments())
                .isShareable(postResponseDto.getIsShareable())
                .build();
    }

}
