package com.tony.linktalk.adapter.out.persistence.entity.constant.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatMessageStatus {

    SENT("SENT", "전송"),
    DELIVERED("DELIVERED", "전달"),
    READ("READ", "읽음"),
    DELETED("DELETED", "삭제")

    ;

    private final String code;
    private final String description;

}
