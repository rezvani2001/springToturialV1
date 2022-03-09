package com.example.demo.Models.messages;

public enum LessonMessages implements MessagesInterface {
    ADDED(200, "lesson added successfully", "درس با موفقیت ثبت شد"),
    STUDENT_NOT_FOUND(404, "student does not belong to this lesson", "این دانشجو در این درس ثبت نشده است"),
    NOT_FOUND(404, "no lesson found with provided id", "درسی با مشخصه مورد نطر یافت نشد");


    private final int statusCode;
    private final String enMessage;
    private final String faMessage;

    LessonMessages(int statusCode, String enMessage, String faMessage) {
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
