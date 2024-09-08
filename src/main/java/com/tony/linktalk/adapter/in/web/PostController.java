package com.tony.linktalk.adapter.in.web;

import com.tony.linktalk.adapter.in.web.dto.api.ApiResponse;
import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;
import com.tony.linktalk.application.command.post.CreatePostCommand;
import com.tony.linktalk.application.command.post.FindPostCommand;
import com.tony.linktalk.application.port.in.post.CreatePostUseCase;
import com.tony.linktalk.application.port.in.post.DeletePostUseCase;
import com.tony.linktalk.application.port.in.post.FindPostUseCase;
import com.tony.linktalk.application.port.in.post.UpdatePostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        FindPostCommand command = FindPostCommand.of(postId);
        PostResponseDto responseDto = findPostUseCase.findPost(command);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createPost(
            @RequestBody PostResponseDto postResponseDto
    ) {
        CreatePostCommand command = CreatePostCommand.of(postResponseDto);
        PostResponseDto responseDto = createPostUseCase.createPost(command);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

}
