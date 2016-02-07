package com.nexters.rainbow.rainbowcouple.auth;

public class SignUpForm {
    private String userId;
    private String userName;
    private String password;

    public String getUserId() {
        return userId;
    }

    public SignUpForm userId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SignUpForm userName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SignUpForm password(String password) {
        this.password = password;
        return this;
    }

    public static SignUpForm builder() {
        return new SignUpForm();
    }
}
