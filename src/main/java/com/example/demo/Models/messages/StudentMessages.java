package com.example.demo.Models.messages;

public enum StudentMessages implements MessagesInterface {
    ADDED(200, "student added successfully", "دانشجو با موفقیت ثبت شد"),

    NOT_FOUND(404, "no student found with provided id", "دانشجویی با مشخصه مورد نطر یافت نشد");

    final int statusCode;
    final String ENMessage;
    final String FAMessage;

    StudentMessages(int statusCode, String ENMessage, String FAMessage) {
        this.statusCode = statusCode;
        this.ENMessage = ENMessage;
        this.FAMessage = FAMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getEnMessage() {
        return ENMessage;
    }

    public String getFaMessage() {
        return FAMessage;
    }
}
