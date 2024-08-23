package com.tony.linktalk.adapter.out.persistence.entity.constant.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatMessageType {

    TEXT("TEXT", "텍스트"),
    IMAGE("IMAGE", "이미지"),
    FILE("FILE", "파일"),
    VIDEO("VIDEO", "비디오"),
    AUDIO("AUDIO", "오디오")

    ;

    private final String code;
    private final String description;

}
