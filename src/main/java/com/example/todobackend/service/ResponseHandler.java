package com.example.todobackend.service;

public class ResponseHandler {
    private String reason;
    private int status;

    public ResponseHandler(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }
    public int getStatus() {
        return this.status;
    }
}
