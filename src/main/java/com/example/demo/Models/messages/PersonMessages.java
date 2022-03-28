package com.example.demo.Models.messages;

import org.springframework.http.ResponseEntity;

public enum PersonMessages implements MessagesInterface {
    ADDED(200, "teacher added successfully", "شخص با موفقیت اضافه شد"),
    DUPLICATED_NATIONAL_CODE(400, "duplicated entry for national code", "شماره ملی وارد شده تکراری است"),
    NOT_FOUND(404, "no teacher found with provided id", "شخصی با مشخصه مورد نطر یافت نشد");

    private final int statusCode;
    private final String enMessage;
    private final String faMessage;

    PersonMessages(int statusCode, String enMessage, String faMessage) {
        this.statusCode = statusCode;
        this.enMessage = enMessage;
        this.faMessage = faMessage;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getEnMessage() {
        return this.enMessage;
    }

    @Override
    public String getFaMessage() {
        return this.faMessage;
    }
}
