package com.example.demo.global.exception.exceptionCollection;

import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TokenExpirationException extends RuntimeException {
    private final ErrorCode errorCode;
    public TokenExpirationException(String message) {
        super(message);
        errorCode = ErrorCode.TOKEN_EXPIRATION;
    }
}