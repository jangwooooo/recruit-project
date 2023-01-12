package com.example.auth.global.exception.handler;

import com.example.auth.domain.auth.exception.EmailAlreadyExistException;
import com.example.auth.domain.auth.exception.NotVerifyEmailException;
import com.example.auth.domain.auth.exception.UserNotFoundException;
import com.example.auth.domain.email.exception.EmailSendFailException;
import com.example.auth.domain.email.exception.AuthCodeMismatchException;
import com.example.auth.global.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailSendFailException.class)
    public ResponseEntity<ErrorResponse> EmailSendFailHandler(HttpServletRequest request, EmailSendFailException ex) {
        log.warn("EmailSendFailException 발생!!! url:{}, trace:{}", request.getRequestURI(), ex.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().getMessage(), ex.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundHandler(UserNotFoundException exception, HttpServletRequest request) {
        log.warn("UserNotFoundException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(AuthCodeMismatchException.class)
    public ResponseEntity<ErrorResponse> AuthCodeExpiredHandler(AuthCodeMismatchException exception, HttpServletRequest request) {
        log.warn("AuthCodeMismatchException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> EmailAlreadyExistHandler(EmailAlreadyExistException exception, HttpServletRequest request) {
        log.warn("EmailAlreadyExistException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(NotVerifyEmailException.class)
    public ResponseEntity<ErrorResponse> NotVerifyEmailHandler(NotVerifyEmailException exception, HttpServletRequest request) {
        log.warn("NotVerifyEmailException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }
}
