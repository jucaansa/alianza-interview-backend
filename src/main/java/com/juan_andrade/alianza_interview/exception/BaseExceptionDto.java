package com.juan_andrade.alianza_interview.exception;

public class BaseExceptionDto {
    private int Status;
    private String error;
    private String message;

    public BaseExceptionDto(){}

    public BaseExceptionDto(String error, String message, int status) {
        this.error = error;
        this.message = message;
        Status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
