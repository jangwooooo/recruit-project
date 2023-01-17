package com.example.demo.global.exception.handler;

import com.example.demo.domain.auth.exception.*;
import com.example.demo.domain.board.exception.BoardNotFoundException;
import com.example.demo.domain.email.exception.AuthCodeExpiredException;
import com.example.demo.domain.email.exception.AuthCodeMismatchException;
import com.example.demo.domain.email.exception.ManyRequestEmailAuthException;
import com.example.demo.domain.user.exception.NameAlreadyExistException;
import com.example.demo.domain.user.exception.PasswordWrongException;
import com.example.demo.domain.user.exception.UserNotFoundException;
import com.example.demo.global.exception.ErrorCode;
import com.example.demo.global.exception.ErrorResponse;
import com.example.demo.global.exception.exceptionCollection.TokenExpirationException;
import com.example.demo.global.exception.exceptionCollection.TokenNotValidException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("MethodArgumentNotValidException 발생!!! url:{}, trace:{}", request.getRequestURI(), e.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_NULL.getMessage(), ErrorCode.NOT_NULL.getStatus());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> methodTypeMisException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.warn("MethodArgumentTypeMismatchException 발생!!! url:{}, trace:{}", request.getRequestURI(), e.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_NULL.getMessage(), ErrorCode.NOT_NULL.getStatus());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    // 유저를 찾을 수 없습니다
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request) {
        log.warn("UserNotFoundException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }
    @ExceptionHandler(NotVerifyEmailException.class)
    public ResponseEntity<ErrorResponse> handleNotVerifyEmail(NotVerifyEmailException exception, HttpServletRequest request) {
        log.warn("NotVerifyEmailException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    // 비밀번호가 일치하지 않습니다
    @ExceptionHandler(PasswordWrongException.class)
    public ResponseEntity<ErrorResponse> handlePasswordWrongException( PasswordWrongException exception,HttpServletRequest request) {
        log.warn("handlePasswordWrongException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }
    @ExceptionHandler(BlackListAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleBlackListAlreadyExistException(BlackListAlreadyExistException exception,HttpServletRequest request) {
        log.warn("handleMisMatchPasswordException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    // 이미 존재하는 이메일 입니다
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistException(EmailAlreadyExistException exception,HttpServletRequest request) {
        log.warn("handleExistEmailException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(NameAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> NameAlreadyExistException(NameAlreadyExistException exception,HttpServletRequest request) {
        log.warn("NameAlreadyExistException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(AuthCodeExpiredException.class)
    public ResponseEntity<ErrorResponse> handleAuthCodeExpiredException(AuthCodeExpiredException exception, HttpServletRequest request) {
        log.warn("handleAuthCodeExpiredException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(ManyRequestEmailAuthException.class)
    public ResponseEntity<ErrorResponse> handleManyRequestEmailAuthException(HttpServletRequest request , ManyRequestEmailAuthException exception) {
        log.warn("handleManyRequestEmailAuthException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(AuthCodeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleAuthCodeMismatchException(HttpServletRequest request , AuthCodeMismatchException exception) {
        log.warn("handleAuthCodeMismatchException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException(HttpServletRequest request , RefreshTokenNotFoundException exception) {
        log.warn("handleRefreshTokenNotFoundException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }
    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpirationException(HttpServletRequest request, TokenExpirationException exception) {
        log.warn("TokenExpirationException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<ErrorResponse> TokenNotValidException(HttpServletRequest request, TokenNotValidException exception) {
        log.warn("TokenNotValidException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponse> BoardNotFoundException( BoardNotFoundException exception,HttpServletRequest request) {
        log.warn("BoardNotFoundException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    /*
     * HTTP 405 Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpServletRequest request , RefreshTokenNotFoundException exception) {
        log.warn("HttpRequestMethodNotSupportedException 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }
    /*
     * HTTP 500 Exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request , RefreshTokenNotFoundException exception) {
        log.warn("Exception 발생!!! url:{}, trace:{}", request.getRequestURI(), exception.getStackTrace());
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getMessage(), exception.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }
}

