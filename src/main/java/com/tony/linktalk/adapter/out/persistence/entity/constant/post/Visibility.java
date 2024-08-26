package com.tony.linktalk.adapter.out.persistence.entity.constant.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Visibility {

    PUBLIC("public", "공개"),
    PRIVATE("private", "비공개"),
    FRIENDS("friends", "친구만"),
    FOLLOWERS("followers", "팔로워만"),
    CUSTOM("custom", "커스텀")

    ;

    private final String code;
    private final String description;

    public static Visibility from(String value) {
        for (Visibility visibility : values()) {
            if (visibility.name().equalsIgnoreCase(value)) {
                return visibility;
            }
        }
        return PUBLIC;
    }

}
