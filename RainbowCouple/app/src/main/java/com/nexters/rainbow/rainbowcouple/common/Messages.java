package com.nexters.rainbow.rainbowcouple.common;

public abstract class Messages {

    public static final String EXCEPTION = "에러가 발생하였습니다.";
    public static final String UNKNOWN_ERROR = "알 수 없는 오류가 발생하였습니다.\n에러내용 : %s";
    public static final String BAD_REQUEST = "잘못된 요청입니다.";
    public static final String PROGRESS_LOADING_MESSAGE = "잠시만 기다려 주세요.";

    public static abstract class LoginError {
        public static final String INVALID_SESSION = "로그인 세션 값이 올바르지 않습니다.";
        public static final String INVALID_ID_PASSWORD = "ID 혹은 패스워드가 잘 못 입력 되었습니다.";
        public static final String EMPTY_LOGIN_ID = "로그인 ID를 입력 해 주세요.";
        public static final String EMPTY_LOGIN_PASSWORD = "로그인 패스워드를 입력 해 주세요.";
    }

    public static abstract class SignUpError {
        public static final String INVALID_ID = "ID를 올바른 형식으로 입력 해 주세요.";
        public static final String INVALID_PASSWORD_SIZE = "패스워드는 6자리 이상 입력 해 주세요.";
        public static final String EMPTY_USER_ID = "ID를 입력 해 주세요.";
        public static final String EMPTY_USER_PASSWORD = "패스워드를 입력 해 주세요.";
        public static final String EMPTY_USER_NAME = "이름을 입력 해 주세요.";
    }

    public static abstract class NetworkError {
        public static final String HTTP_SERVER_ERROR = "%s";
        public static final String NETWORK_ERROR = "서버와 통신 중 오류가 발생했습니다.\n에러내용 : %s";
        public static final String UNKNOWN_NETWORK_ERROR = "서버와 통신 중 알 수 없는 오류가 발생했습니다.\n에러내용 : %s";
    }

}
