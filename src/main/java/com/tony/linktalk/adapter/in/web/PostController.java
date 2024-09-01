package com.tony.linktalk.adapter.in.web;

import com.tony.linktalk.adapter.in.web.dto.api.ApiResponse;
import com.tony.linktalk.application.port.in.post.CreatePostUseCase;
import com.tony.linktalk.application.port.in.post.DeletePostUseCase;
import com.tony.linktalk.application.port.in.post.FindPostUseCase;
import com.tony.linktalk.application.port.in.post.UpdatePostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<?>> findPost() {

        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
