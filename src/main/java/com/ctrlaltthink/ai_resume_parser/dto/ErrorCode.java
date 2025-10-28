package com.ctrlaltthink.ai_resume_parser.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND", "User not found with id %s", HttpStatus.NOT_FOUND),
    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH", "Password Mismatch", HttpStatus.BAD_REQUEST),
    INVALID_CURRENT_PASSWORD("INVALID_CURRENT_PASSWORD","Invalid Current Password", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_DEACTIVATE("ACCOUNT_ALREADY_DEACTIVATE","Account Already Inactive", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_ACTIVE("ACCOUNT_ALREADY_ACTIVE","Account Already Active", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS","Email Already Exists", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH", "Password Mismatch", HttpStatus.BAD_REQUEST),
    ERR_USER_DISABLED("ERR_USER_DISABLED", "User account is disabled, please activate your account or contact the administrator", UNAUTHORIZED),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "Username and / or password is incorrect", UNAUTHORIZED),
    INTERNAL_EXCEPTION("INTERNAL_EXCEPTION", "An internal exception occurred, please try again or contact the admin", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND", "Cannot find user with the provided username", NOT_FOUND),
    CATEGORY_ALREADY_EXISTS_FOR_USER("CATEGORY_ALREADY_EXISTS_FOR_USER", "Category already exists for this user", CONFLICT),
    ;
    private final String code;
    private final String defaultMessage;
    private final HttpStatus status;

    ErrorCode(String code, String defaultMessage, HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }



}
