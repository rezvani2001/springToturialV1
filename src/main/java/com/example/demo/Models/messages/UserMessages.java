package com.example.demo.Models.messages;

public enum UserMessages implements MessagesInterface{
    DUPLICATED_USERNAME(400, "duplicated entry for username", "نام کاربری واردشده تکراری است"),
    NOT_FOUND(404, "no user found with provided username", "کاربری با نام کاربری داده شده یافت نشد"),
    WRONG_PASSWORD(400, "wrong password", "کلمه عبور اشتباه است"),
    NOT_AUTHENTICATED(401, "not authenticated", "احراز هویت نشده");

    private final int statusCode;
    private final String enMessage;
    private final String faMessage;

    UserMessages(int statusCode, String enMessage, String faMessage) {
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
