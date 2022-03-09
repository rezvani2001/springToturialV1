package com.example.demo.Repositories;

import com.example.demo.Models.Lesson;
import com.example.demo.Models.Student;
import com.example.demo.Models.StudentLesson;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentLessonRepository extends CrudRepository<StudentLesson, UUID> {
    Optional<StudentLesson> findStudentLessonByStudentAndLesson(Student student, Lesson lesson);
}
