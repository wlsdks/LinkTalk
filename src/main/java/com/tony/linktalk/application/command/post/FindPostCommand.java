package com.tony.linktalk.application.command.post;

import com.tony.linktalk.adapter.out.persistence.entity.constant.post.Visibility;
import com.tony.linktalk.domain.Member;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class FindPostCommand {

    private Long id;
    private String title;
    private Visibility visibility = Visibility.PUBLIC;
    private String status = "active";
    private Boolean isPinned = false;
    private String content;  // 게시글 내용
    private Boolean allowComments = true;
    private Boolean isShareable = true;
    private Member member;  // 작성자 정보


    // factory method
    public static FindPostCommand of(Long postId) {
        return FindPostCommand.builder()
                .id(postId)
                .build();
    }

}
