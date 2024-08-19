package com.tony.linktalk.adapter.out.persistence.entity.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 알림 유형을 정의하는 Enum
 */
@Getter
@AllArgsConstructor
public enum NotificationType {

    MESSAGE_RECEIVED("MESSAGE_RECEIVED", "메시지 수신"),
    FRIEND_REQUEST("FRIEND_REQUEST", "친구 요청"),
    SYSTEM_ALERT("SYSTEM_ALERT", "시스템 알림"),
    OTHER("OTHER", "기타");

    private final String code;
    private final String description;

    // find NotificationType by code
    public static NotificationType fromCode(String code) {
        return Arrays.stream(NotificationType.values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(OTHER);
    }

}
