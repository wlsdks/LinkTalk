package com.tony.linktalk.application.service.post;

import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;
import com.tony.linktalk.application.command.post.UpdatePostCommand;
import com.tony.linktalk.application.port.in.post.UpdatePostUseCase;
import com.tony.linktalk.application.port.out.post.UpdatePostPort;
import com.tony.linktalk.domain.post.Post;
import com.tony.linktalk.mapper.PostMapper;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@UseCase
public class UpdatePostService implements UpdatePostUseCase {

    private final UpdatePostPort updatePostPort;
    private final PostMapper postMapper;

    @Transactional
    @Override
    public PostResponseDto updatePost(UpdatePostCommand command) {
        Post post = postMapper.commandToDomain(command);
        Post updatedPost = updatePostPort.updatePost(post);

        return postMapper.domainToResponseDto(updatedPost);
    }

}
