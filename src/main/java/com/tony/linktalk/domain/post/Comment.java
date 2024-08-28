package com.tony.linktalk.domain.post;

import com.tony.linktalk.domain.Member;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class Comment {

    private Long id;
    private String content;
    private Member member; // 댓글 작성자
    private Post post; // 해당 댓글이 달린 게시글
    private Long likesCount = 0L;

}
