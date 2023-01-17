package com.example.demo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("유저를 찾을 수 없습니다.",404),
    BOARD_NOT_FOUND("게시글을 찾을 수 없습니다.",404),
    NOT_NULL("null값은 허용되지 않습니다.", 400),
    ALREADY_EXIST_NAME("이미 존재하는 닉네임입니다.", 400),
    NOT_VERIFY_EMAIL("이메일이 확인 되지 않음", 401),
    ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다." , 400),

    NOTMATCH_MEMBER_PASSWORD("비밀번호가 일치하지 않습니다.", 400),


    BLACK_LIST_ALREADY_EXIST("블랙리스트에 이미 존재합니다.",409),

    TOKEN_EXPIRATION("토큰이 만료 되었습니다.", 401),
    TOKEN_NOT_VALID("토큰이 유효하지 않습니다.", 401),
    REFRESH_TOKEN_NOT_FOUND("존재하지 않는 리프레시 토큰입니다.", 404),

    MANY_REQUEST_EMAIL_AUTH("15분에 최대 3번의 이메일 요청만 가능합니다." , 429),
    EXPIRE_EMAIL_CODE("이메일 인증번호 시간이 만료되었습니다.", 401),
    MISMATCH_AUTH_CODE("인증번호가 일치하지 않습니다." , 400),
    BAD_REQUEST("잘못된 요청 입니다.",400),
    POSTS_NOT_FOUND("게시글 정보를 찾을 수 없습니다.",404),
    METHOD_NOT_ALLOWED( "허용되지 않은 메서드입니다.",405),
    INTERNAL_SERVER_ERROR( "내부 서버 오류입니다.",500),
    APPLICANTS_FULL_UP("신청자리가 꽉 찼습니다.",400),
    ALREADY_APPLICATION("이미 신청한 유저입니다", 400),
    ;

    private final String message;
    private final int status;
}
