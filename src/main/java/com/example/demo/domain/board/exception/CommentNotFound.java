package com.example.demo.domain.board.exception;

import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CommentNotFound extends RuntimeException{

    private final ErrorCode errorCode;

    public CommentNotFound(String message) {
        super(message);
        this.errorCode = ErrorCode.COMMENT_NOT_FOUND;
    }
}
