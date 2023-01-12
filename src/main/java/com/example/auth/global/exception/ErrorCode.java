package com.example.auth.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_SEND_FAIL("메일 발송에 실패 했습니다",500),
    USER_NOT_FOUND("유저를 찾을 수 없습니다.",404),
    AUTH_CODE_MISMATCH("인증번호가 일치하지 않습니다.",400),
    EMAIL_ALREADY_EXIST("이메일이 이미 DB에 존재합니다.", 409),
    NOT_VERIFY_EMAIL("인증되지 않은 이메일입니다.",401),
    ;

    private final String message;
    private final int status;
}
