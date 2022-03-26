package com.example.demo.Models.messages;

public enum CollegeMEssages implements MessagesInterface {
    ADDED(200, "college added successfully", "دانشکده با موفقیت ثبت شد"),
    DUPLICATED_NAME(400, "duplicated entry for name", "نام وارد شده تکراری است"),
    REQUIRED_STDID_CLGID(400, "college id and student id are required", "college id و student id را وارد کنید"),
    REQUIRED_TEACHERID_CLGID(400, "college id and teacher id are required", "college id و teacher id را وارد کنید"),
    REQUIRED_LESSONID_CLGID(400, "college id and lesson id are required", "college id و lesson id را وارد کنید"),

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
