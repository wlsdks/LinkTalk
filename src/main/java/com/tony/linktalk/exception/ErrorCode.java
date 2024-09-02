package com.tony.linktalk.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // common errors
    UNEXPECTED_ERROR("LINK99999", "Unexpected error"),

    // member
    MEMBER_NOT_FOUND("LINK00001", "Member not found"),
    MEMBER_JWT_ALREADY_EXIST("LINK00002", "Member JWT already exist"),
    MEMBER_REQUIRED_VALUE("LINK00003", "Member required value"),
    MEMBER_EMAIL_NOT_MATCH("LINK00004", "Member getEmail not match"),
    MEMBER_INNER_EMAIL_NOT_FOUND("LINK00005", "Member inner getEmail not found"),
    MEMBER_EMAIL_PARAM_NOT_FOUND("LINK00006", "Member getEmail param not found"),
    CHANGE_EMAIL_VALUE_NOT_FOUND("LINK00007", "Change getEmail value not found"),

    // refresh token
    REFRESH_TOKEN_NOT_HAVE_MEMBER("REF001", "Refresh token does not have a member"),
    REFRESH_TOKEN_EXPIRATION_DATE_NOT_FOUND("REF002", "Refresh token expiration date not found"),
    REFRESH_TOKEN_EXPIRED("REF003", "Refresh token expired"),
    DATA_NOT_FOUND("DATA001", "Data not found"),

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
    SECURITY_USER_NOT_FOUND("SEC001", "Security user not found"),
    PASSWORD_IS_NOT_HANDLE_IN_SECURITY_USER_DETAILS("SEC002", "Password is not handle in security user details"),

    // post errors
    POST_NOT_FOUND("POST001", "Post not found"),


    ;

    private final String code;
    private final String message;

}
