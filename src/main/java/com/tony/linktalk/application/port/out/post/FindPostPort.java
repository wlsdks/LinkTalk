package com.tony.linktalk.application.port.out.post;

import com.tony.linktalk.domain.post.Post;

public interface FindPostPort {

    Post findPostById(Long id);

}
