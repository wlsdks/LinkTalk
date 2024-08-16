package com.tony.linktalk.exception;

import com.tony.linktalk.adapter.in.web.dto.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역적으로 발생하는 예외를 처리하는 클래스
 */
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 커스텀 예외인 MemberException을 처리하기 위해 사용됩니다.
     */
    @ExceptionHandler(LinkTalkException.class)
    public ResponseEntity<ApiResponse<String>> handleMemberException(LinkTalkException e) {
        ApiResponse<String> response = ApiResponse.fail(e.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * 애플리케이션에서 발생하는 모든 예외를 처리하기 위해 사용됩니다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        ApiResponse<String> response = ApiResponse.fail(ErrorCode.UNEXPECTED_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
