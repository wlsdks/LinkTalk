package com.tony.linktalk.adapter.out.persistence.entity.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OutboxMessageStatus {

    PENDING("PENDING", "대기"),
    PROCESSED("PROCESSED", "처리완료"),
    SUCCESS("SUCCESS", "성공"),
    FAIL("FAIL", "실패");

    private final String code;
    private final String description;


    /**
     * 코드로 OutboxMessageStatus 찾기
     *
     * @param code 코드
     * @return OutboxMessageStatus
     */
    public static OutboxMessageStatus of(String code) {
        return Arrays.stream(OutboxMessageStatus.values())
                .filter(outboxMessageStatus -> outboxMessageStatus.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid OutboxMessageStatus code: " + code));
    }

}
