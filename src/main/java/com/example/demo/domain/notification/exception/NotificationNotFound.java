package com.example.demo.domain.notification.exception;

import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotificationNotFound extends RuntimeException{

    private final ErrorCode errorCode;

    public NotificationNotFound(String message) {
        super(message);
        this.errorCode = ErrorCode.NOTIFICATION_NOT_FOUND;
    }
}
