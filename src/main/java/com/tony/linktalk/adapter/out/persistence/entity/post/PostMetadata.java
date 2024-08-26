package com.tony.linktalk.adapter.out.persistence.entity.post;

import com.tony.linktalk.adapter.out.persistence.entity.constant.post.Visibility;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Embeddable
public class PostMetadata {

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private Visibility visibility = Visibility.PUBLIC;

    @Column(name = "status")
    private String status = "active";

    @Column(name = "is_pinned")
    private Boolean isPinned = false;

}
