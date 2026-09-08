package com.schoolmanagement.schoolmanagementwebsite.dto;

public class WebSocketMessage {

    private String message;

    public WebSocketMessage() {
    }

    public WebSocketMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}