package com.nexters.rainbow.rainbowcouple.common.network;

public class ErrorResponse {
    private int errorCode;
    private String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("errorCode : ")
                .append(errorCode)
                .append("\nerrorMessage")
                .append(errorMessage);
        return builder.toString();
    }
}
