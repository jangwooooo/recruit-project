package com.example.auth.domain.email.exception;

import com.example.auth.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailSendFailException extends RuntimeException{
    private final ErrorCode errorCode;

    public EmailSendFailException(String message){
        super(message);
        this.errorCode = ErrorCode.EMAIL_SEND_FAIL;
    }
}
