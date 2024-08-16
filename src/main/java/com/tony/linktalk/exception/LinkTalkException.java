package com.tony.linktalk.exception;

import lombok.Getter;

@Getter
public class LinkTalkException extends RuntimeException {

    private final ErrorCode errorCode;

    public LinkTalkException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public LinkTalkException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

}
