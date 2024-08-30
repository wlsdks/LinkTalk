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
    INVALID_USER_EMAIL("MEM005", "Invalid user getEmail"),
    INVALID_USER_PHONE("MEM006", "Invalid user phone"),

    // error codes for unexpected errors
    UNEXPECTED_ERROR("ERR001", "Unexpected error"),
    CHANGE_EMAIL_VALUE_NOT_FOUND("ERR002", "Change getEmail value not found"),
    MEMBER_JWT_ALREADY_EXIST("ERR003", "Member JWT already exist"),
    MEMBER_REQUIRED_VALUE("MEM007", "Member required value"),


    // refresh token errors
    REFRESH_TOKEN_NOT_HAVE_MEMBER("REF001", "Refresh token does not have a member"),
    REFRESH_TOKEN_EXPIRATION_DATE_NOT_FOUND("REF002", "Refresh token expiration date not found"),
    REFRESH_TOKEN_EXPIRED("REF003", "Refresh token expired"),

    // data not found errors
    DATA_NOT_FOUND("DATA001", "Data not found"),

    // authentication errors
    AUTHENTICATION_NOT_FOUND("AUTH001", "Authentication not found"),


    // member errors
    MEMBER_EMAIL_NOT_MATCH("MEM008", "Member getEmail not match"),
    MEMBER_INNER_EMAIL_NOT_FOUND("MEM009", "Member inner getEmail not found"),
    MEMBER_EMAIL_PARAM_NOT_FOUND("MEM010", "Member getEmail param not found"),


    // file errors
    FILE_UPLOAD_ERROR("FILE001", "File upload error"),
    FILE_DOWNLOAD_ERROR("FILE002", "File download error"),
    FILE_NOT_FOUND("FILE003", "File not found"),
    INVALID_FILE_TYPE("FILE004", "Invalid file type"),


    // chat room errors
    CHAT_MESSAGE_ID_NOT_FOUND("CHAT001", "Chat message ID not found"),
    CHAT_MESSAGE_STATUS_NOT_FOUND("CHAT002", "Chat message status not found"),
    CHAT_MESSAGE_NOT_FOUND("CHAT003", "Chat message not found"),

    // security errors
    UNAUTHORIZED("UNAUTHORIZED", "Unauthorized"),
    AUTHENTICATION_ERROR("AUTHENTICATION_ERROR", "Authentication error"),
    PRINCIPAL_CAST_ERROR("PRINCIPAL_CAST_ERROR", "Principal cast error"),
    SECURITY_USER_NOT_FOUND("SEC001", "Security user not found"),;

    private final String code;
    private final String message;

}
