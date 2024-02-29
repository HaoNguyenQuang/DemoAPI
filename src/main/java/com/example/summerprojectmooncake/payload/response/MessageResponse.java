package com.example.summerprojectmooncake.payload.response;

import org.springframework.http.HttpStatus;

public class MessageResponse {
    private int code;
    private String message;
    public MessageResponse() {
    }

    public MessageResponse(String message) {
        this.message=message;
    }

    public MessageResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
