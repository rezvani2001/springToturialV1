package com.example.demo.Models.messages;

public enum CollegeMEssages implements MessagesInterface {
    ADDED(200, "college added successfully", "دانشکده با موفقیت ثبت شد"),
    DUPLICATED_NAME(400, "duplicated entry for name", "نام وارد شده تکراری است"),
    NOT_FOUND(404, "no college found with provided id", "دانشکده با مشخصه مورد نطر یافت نشد");

    final int statusCode;
    final String ENMessage;
    final String FAMessage;

    CollegeMEssages(int statusCode, String ENMessage, String FAMessage) {
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
