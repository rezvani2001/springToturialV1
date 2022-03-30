package com.example.demo.Models.messages;


public enum LessonMessages implements MessagesInterface {
    ADDED(200, "lesson added successfully", "درس با موفقیت ثبت شد"),
    STUDENT_ASSIGNED(200, "student added to lesson", "دانشجو به درس اضافه شد"),
    TEACHER_ASSIGNED(200, "teacher added to lesson", "استاد به درس اضافه شد"),
    STUDENT_GRADED(200, "student grade has been set", "نمره دانشجو ثبت شد"),

    STUDENT_NOT_FOUND(404, "student does not belong to this lesson", "این دانشجو در این درس ثبت نشده است"),
    REQUIRED_TEACHERID_LESSONID(400, "lesson id and teacher id are required", "lesson id و teacher id را وارد کنید"),
    REQUIRED_STDID_LESSONID(400, "lesson id and student id are required", "lesson id و student id را وارد کنید"),
    REQUIRED_GRADE(400, "lesson id, student id and grade id are required", "lesson id، student id و نمره را وارد کنید"),
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
