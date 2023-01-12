package com.example.auth.domain.auth.exception;

import com.example.auth.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public UserNotFoundException(String message){
        super(message);
        this.errorCode = ErrorCode.USER_NOT_FOUND;
    }
}
