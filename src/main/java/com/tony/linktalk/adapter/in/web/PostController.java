package com.tony.linktalk.adapter.in.web;

import com.tony.linktalk.adapter.in.web.dto.api.ApiResponse;
import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;
import com.tony.linktalk.application.command.post.PostCommand;
import com.tony.linktalk.application.port.in.post.CreatePostUseCase;
import com.tony.linktalk.application.port.in.post.DeletePostUseCase;
import com.tony.linktalk.application.port.in.post.FindPostUseCase;
import com.tony.linktalk.application.port.in.post.UpdatePostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/post")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final FindPostUseCase findPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;


    /**
     * @param postId 게시글 ID
     * @return 게시글 단건 조회 결과
     * @apiNote 게시글 단건 조회
     */
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<?>> findPost(
            @PathVariable("postId") Long postId
    ) {
        PostCommand.Find command = PostCommand.Find.of(postId);
        PostResponseDto responseDto = findPostUseCase.findPost(command);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

}
