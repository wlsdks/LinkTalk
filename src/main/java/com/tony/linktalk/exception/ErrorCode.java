package com.tony.linktalk.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // authentication errors
    MEMBER_NOT_FOUND("MEM001", "Member not found"),
    MEMBER_ALREADY_EXISTS("MEM002", "User already exists"),
    INVALID_USER_ID("MEM003", "Invalid user ID"),
    INVALID_USER_NAME("MEM004", "Invalid user name"),
    INVALID_USER_EMAIL("MEM005", "Invalid user email"),
    INVALID_USER_PHONE("MEM006", "Invalid user phone"),

    // error codes for unexpected errors
    UNEXPECTED_ERROR("ERR001", "Unexpected error"),
    CHANGE_EMAIL_VALUE_NOT_FOUND("ERR002", "Change email value not found"),
    MEMBER_JWT_ALREADY_EXIST("ERR003", "Member JWT already exist"),
    MEMBER_REQUIRED_VALUE("MEM007", "Member required value"),


    // refresh token errors
    REFRESH_TOKEN_NOT_HAVE_MEMBER("REF001", "Refresh token does not have a member"),
    REFRESH_TOKEN_EXPIRATION_DATE_NOT_FOUND("REF002", "Refresh token expiration date not found"),
    REFRESH_TOKEN_EXPIRED("REF003", "Refresh token expired"),


    DATA_NOT_FOUND("DATA001", "Data not found"),


    ;

    private final String code;
    private final String message;

}
