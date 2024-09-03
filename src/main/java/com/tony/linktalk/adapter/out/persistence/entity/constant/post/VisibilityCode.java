package com.tony.linktalk.adapter.out.persistence.entity.constant.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VisibilityCode {

    PUBLIC("public", "공개"),
    PRIVATE("private", "비공개"),
    FRIENDS("friends", "친구만"),
    FOLLOWERS("followers", "팔로워만"),
    CUSTOM("custom", "커스텀")

    ;

    private final String code;
    private final String description;

    public static VisibilityCode from(String value) {
        for (VisibilityCode visibilityCode : values()) {
            if (visibilityCode.name().equalsIgnoreCase(value)) {
                return visibilityCode;
            }
        }
        return PUBLIC;
    }

}
