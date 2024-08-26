package com.tony.linktalk.adapter.out.persistence.entity.constant.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MediaType {

    IMAGE("image", "이미지"),
    VIDEO("video", "동영상"),
    AUDIO("audio", "오디오"),
    DOCUMENT("document", "문서"),
    LINK("link", "링크"),
    OTHER("other", "기타");

    private final String code;
    private final String description;

    public static MediaType from(String value) {
        for (MediaType mediaType : values()) {
            if (mediaType.name().equalsIgnoreCase(value)) {
                return mediaType;
            }
        }
        return OTHER;
    }

}
