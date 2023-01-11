package com.example.demo.global.exception.handler;

import com.example.demo.global.exception.ErrorCode;
import com.example.demo.global.exception.ErrorResponse;
import com.example.demo.global.exception.exceptionCollection.TokenExpirationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<ErrorResponse> TokenExpirationException(TokenExpirationException e, HttpServletRequest request) {
        log.warn("TokenExpirationException 발생!!! url:{}, trace:{}", request.getRequestURI(), e.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_NULL.getMessage(), ErrorCode.NOT_NULL.getStatus());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}

