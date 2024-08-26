package com.tony.linktalk.adapter.out.persistence.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class PostSettings {

    @Column(name = "allow_comments")
    private Boolean allowComments = true;

    @Column(name = "is_shareable")
    private Boolean isShareable = true;

}
