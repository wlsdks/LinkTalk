package com.tony.linktalk.application.service.post;

import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;
import com.tony.linktalk.application.command.post.CreatePostCommand;
import com.tony.linktalk.application.port.in.post.CreatePostUseCase;
import com.tony.linktalk.application.port.out.post.CreatePostPort;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@UseCase
public class CreatePostService implements CreatePostUseCase {

    private final CreatePostPort createPostPort;


    /**
     * @param command 게시글 생성 요청
     * @return 게시글 생성 결과
     * @apiNote 게시글 생성
     */
    @Transactional
    @Override
    public PostResponseDto createPost(CreatePostCommand command) {


        return null;
    }

}
