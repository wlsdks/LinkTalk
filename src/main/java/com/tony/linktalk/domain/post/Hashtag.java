package com.tony.linktalk.domain.post;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class Hashtag {

    private Long id;
    private String tag; // 해시태그 이름

}
