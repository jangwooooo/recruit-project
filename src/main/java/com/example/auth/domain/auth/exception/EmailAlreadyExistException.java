package com.example.auth.domain.auth.exception;

import com.example.auth.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailAlreadyExistException extends RuntimeException {
    private final ErrorCode errorCode;

    public EmailAlreadyExistException(String message) {
        super(message);
        this.errorCode = ErrorCode.EMAIL_ALREADY_EXIST;
    }
}
