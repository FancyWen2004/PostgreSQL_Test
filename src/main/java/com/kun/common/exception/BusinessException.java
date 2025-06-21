package com.kun.common.exception;

// 自定义异常类
public class BusinessException extends RuntimeException {

    private int errorCode;

    private String errorMessage;

    public BusinessException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
