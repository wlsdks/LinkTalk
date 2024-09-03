package com.tony.linktalk.adapter.in.web.dto.response.post;

import com.tony.linktalk.adapter.out.persistence.entity.constant.post.VisibilityCode;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private VisibilityCode visibilityCode = VisibilityCode.PUBLIC;
    private String status = "active";
    private Boolean isPinned = false;
    private String content;  // 게시글 내용
    private Boolean allowComments = true;
    private Boolean isShareable = true;

}
