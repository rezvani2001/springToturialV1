package com.example.demo.services;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.*;
import com.example.demo.Models.messages.LessonMessages;
import com.example.demo.Repositories.LessonRepository;
import com.example.demo.Repositories.StudentLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final StudentLessonRepository studentLessonRepository;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Autowired
    public LessonService(LessonRepository lessonRepository, StudentLessonRepository studentLessonRepository, TeacherService teacherService, StudentService studentService) {
        this.lessonRepository = lessonRepository;
        this.studentLessonRepository = studentLessonRepository;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    public void insertLesson(Lesson lesson) {
        lesson.setId(UUID.randomUUID());
        lessonRepository.save(lesson);
    }

    ////////////////////////////////////////////////////////////////////////////

    public void assignTeacher(UUID teacherId, UUID lessonId) throws GeneralException {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        Lesson lesson = this.getLessonById(lessonId);

        this.grantTeacher(teacher, lesson);
        teacherService.grantLesson(teacher, lesson);
    }

    public void assignStudent(UUID studentId, UUID lessonId) throws GeneralException {
        Student student = studentService.getStudentById(studentId);
        Lesson lesson = this.getLessonById(lessonId);

        StudentLesson studentLesson = new StudentLesson();
        studentLesson.setId(UUID.randomUUID());
        studentLesson.setLesson(lesson);
        studentLesson.setStudent(student);

        studentLessonRepository.save(studentLesson);
        this.grantStudent(studentLesson);
    }

    public double getAvgOfStudents (UUID lessonId) throws GeneralException {
        Lesson lesson = this.getLessonById(lessonId);

        double grade = lesson.getStudents().stream().mapToDouble(StudentLesson::getGrade).sum();

        return grade/ lesson.getStudents().size();
    }

    public void gradeStudent(UUID lessonId, UUID studentId, float grade) throws GeneralException {
        Lesson lesson = this.getLessonById(lessonId);
        Student student = studentService.getStudentById(studentId);

        Optional<StudentLesson> record = studentLessonRepository.findStudentLessonByStudentAndLesson(student, lesson);

        if (record.isEmpty()) {
            throw new GeneralException(LessonMessages.STUDENT_NOT_FOUND);
        } else {
            StudentLesson studentLesson = record.get();

            studentLesson.setGrade(grade);
            studentLessonRepository.save(studentLesson);
        }
    }

    ////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////

    public void grantCollege(Lesson lesson, College college) {
        lesson.setCollege(college);
        lessonRepository.save(lesson);
    }


    public void grantTeacher(Teacher teacher, Lesson lesson) {
        lesson.getTeachers().add(teacher);
        lessonRepository.save(lesson);
    }

    public void grantStudent(StudentLesson studentLesson) {
        studentLesson.getLesson().getStudents().add(studentLesson);
        lessonRepository.save(studentLesson.getLesson());
    }

    ////////////////////////////////////////////////////////////////////////////

    public Lesson getLessonById(UUID lessonId) throws GeneralException {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);

        if (lesson.isPresent()) {
            return lesson.get();
        } else {
            throw new GeneralException(LessonMessages.NOT_FOUND);
        }
    }

    public Set<Lesson> getAllLessons() {
        return (Set<Lesson>) lessonRepository.findAll();
    }
}
