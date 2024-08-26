package com.tony.linktalk.adapter.out.persistence.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class PostMetadata {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "visibility")
    private String visibility = "public";

    @Column(name = "status")
    private String status = "active";

    @Column(name = "is_pinned")
    private Boolean isPinned = false;

}
