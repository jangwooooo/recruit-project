package com.example.demo.domain.auth.exception;

import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailAlreadyExistException extends RuntimeException {

    private final ErrorCode errorCode;

    public EmailAlreadyExistException(String message) {
        super(message);
        this.errorCode = ErrorCode.ALREADY_EXIST_EMAIL;
    }
}