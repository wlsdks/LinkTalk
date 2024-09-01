package com.tony.linktalk.adapter.out.persistence.repository;

import com.tony.linktalk.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
