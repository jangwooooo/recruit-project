package com.example.demo.domain.user.exception;

import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordWrongException extends RuntimeException{
    private final ErrorCode errorCode;

    public PasswordWrongException(String message) {
        super(message);
        this.errorCode = ErrorCode.NOTMATCH_MEMBER_PASSWORD;
    }
}
