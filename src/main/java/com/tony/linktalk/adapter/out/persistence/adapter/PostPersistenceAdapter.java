package com.tony.linktalk.adapter.out.persistence.adapter;

import com.tony.linktalk.adapter.out.persistence.entity.post.PostEntity;
import com.tony.linktalk.adapter.out.persistence.repository.PostRepository;
import com.tony.linktalk.adapter.out.persistence.repository.dsl.PostDslRepository;
import com.tony.linktalk.application.port.out.post.CreatePostPort;
import com.tony.linktalk.application.port.out.post.DeletePostPort;
import com.tony.linktalk.application.port.out.post.FindPostPort;
import com.tony.linktalk.application.port.out.post.UpdatePostPort;
import com.tony.linktalk.domain.post.Post;
import com.tony.linktalk.exception.ErrorCode;
import com.tony.linktalk.exception.LinkTalkException;
import com.tony.linktalk.mapper.PostMapper;
import com.tony.linktalk.util.custom.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class PostPersistenceAdapter implements CreatePostPort, FindPostPort, UpdatePostPort, DeletePostPort {

    private final PostRepository postRepository;
    private final PostDslRepository postDslRepository;
    private final PostMapper postMapper;

    @Override
    public Post findPostById(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new LinkTalkException(ErrorCode.POST_NOT_FOUND));

        return postMapper.entityToDomain(postEntity);
    }


    @Override
    public Post createPost(Post post) {
        PostEntity postEntity = postMapper.domainToEntity(post);
        PostEntity savedPostEntity = postRepository.save(postEntity);

        return postMapper.entityToDomain(savedPostEntity);
    }


    @Override
    public Post updatePost(Post post) {
        PostEntity postEntity = postMapper.domainToEntity(post);
        PostEntity savedPostEntity = postRepository.save(postEntity);

        return postMapper.entityToDomain(savedPostEntity);
    }


    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

}
