package com.tony.linktalk.domain.post;

import com.tony.linktalk.domain.Member;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class Like {

    private Long id;
    private Member member; // 좋아요를 누른 사용자
    private Post post; // 좋아요가 눌린 게시글

}
