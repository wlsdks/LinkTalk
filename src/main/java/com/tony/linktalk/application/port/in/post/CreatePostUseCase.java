package com.tony.linktalk.application.port.in.post;

import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;
import com.tony.linktalk.application.command.post.CreatePostCommand;

public interface CreatePostUseCase {

    PostResponseDto createPost(CreatePostCommand command);

}
