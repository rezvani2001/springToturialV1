package com.example.demo.Models.messages;


public enum TeacherMessages implements MessagesInterface {
    ADDED(200, "teacher added successfully", "استاد با موفقیت اضافه شد"),

    NOT_FOUND(404, "no teacher found with provided id", "استادی با مشخصه مورد نطر یافت نشد");

    private final int statusCode;
    private final String enMessage;
    private final String faMessage;

    TeacherMessages(int statusCode, String enMessage, String faMessage) {
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
