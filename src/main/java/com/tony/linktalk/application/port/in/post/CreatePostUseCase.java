package com.tony.linktalk.application.port.in.post;

import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;

public interface CreatePostUseCase {

    PostResponseDto createPost(CreatePostCommand command);

}
