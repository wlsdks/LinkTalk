package com.tony.linktalk.adapter.out.persistence.repository;

import com.tony.linktalk.adapter.out.persistence.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
