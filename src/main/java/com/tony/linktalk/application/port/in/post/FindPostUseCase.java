package com.tony.linktalk.application.port.in.post;

import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;
import com.tony.linktalk.application.command.post.PostCommand;

public interface FindPostUseCase {

    PostResponseDto findPost(PostCommand.Find findCommand);

}
