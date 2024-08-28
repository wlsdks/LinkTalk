package com.tony.linktalk.domain.post;

import com.tony.linktalk.adapter.out.persistence.entity.constant.post.MediaType;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class Media {

    private Long id;
    private String url; // 미디어 파일의 URL
    private MediaType mediaType; // 이미지, 비디오 등의 미디어 타입
    private Post post; // 해당 미디어가 첨부된 게시글

}
