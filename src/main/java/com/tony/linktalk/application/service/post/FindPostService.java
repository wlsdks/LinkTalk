package com.tony.linktalk.application.service.post;

import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;
import com.tony.linktalk.application.command.post.PostCommand;
import com.tony.linktalk.application.port.in.post.FindPostUseCase;
import com.tony.linktalk.application.port.out.post.FindPostPort;
import com.tony.linktalk.domain.post.Post;
import com.tony.linktalk.mapper.PostMapper;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@UseCase
public class FindPostService implements FindPostUseCase {

    private final FindPostPort findPostPort;
    private final PostMapper postMapper;

    /**
     * @param findCommand 게시글 ID
     * @return 게시글 단건 조회 결과
     * @apiNote 게시글 단건 조회
     */
    @Override
    public PostResponseDto findPost(PostCommand.Find findCommand) {
        Post findPost = findPostPort.findPostById(findCommand.getId());
        return postMapper.domainToResponseDto(findPost);
    }

}
