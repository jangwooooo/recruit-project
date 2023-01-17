package com.example.demo.domain.user.exception;

import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NameAlreadyExistException extends RuntimeException{

    private final ErrorCode errorCode;

    public NameAlreadyExistException(String message) {
        super(message);
        this.errorCode = ErrorCode.ALREADY_EXIST_NAME;
    }
}
