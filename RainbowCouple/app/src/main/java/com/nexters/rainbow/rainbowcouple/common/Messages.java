package com.nexters.rainbow.rainbowcouple.common;

public abstract class Messages {

    public static final String EXCEPTION = "에러가 발생하였습니다.";
    public static final String BAD_REQUEST = "잘못된 요청입니다.";
    public static final String PROGRESS_LOADING_MESSAGE = "잠시만 기다려 주세요.";

    public static abstract class LoginError {
        public static final String INVALID_SESSION = "로그인 세션 값이 올바르지 않습니다.";
        public static final String INVALID_ID_PASSWORD = "ID 혹은 패스워드가 잘 못 입력 되었습니다.";
        public static final String EMPTY_LOGIN_ID = "로그인 ID를 입력 해 주세요.";
        public static final String EMPTY_LOGIN_PASSWORD = "로그인 패스워드를 입력 해 주세요.";
    }

}
