package com.tony.linktalk.application.port.out.post;

import com.tony.linktalk.domain.post.Post;

public interface CreatePostPort {

    Post createPost(Post post);

}
