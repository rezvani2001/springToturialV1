package com.example.demo.Models.messages;


public enum StudentMessages implements MessagesInterface {
    ADDED(200, "student added successfully", "دانشجو با موفقیت ثبت شد"),

    NOT_FOUND(404, "no student found with provided id", "دانشجویی با مشخصه مورد نطر یافت نشد");

    final int statusCode;
    final String enMessage;
    final String faMessage;

    StudentMessages(int statusCode, String ENMessage, String FAMessage) {
        this.statusCode = statusCode;
        this.enMessage = ENMessage;
        this.faMessage = FAMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getEnMessage() {
        return enMessage;
    }

    public String getFaMessage() {
        return faMessage;
    }
}
