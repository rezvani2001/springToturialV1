package com.example.demo.Repositories;

import com.example.demo.Models.StudentLesson;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StudentLessonRepository extends CrudRepository<StudentLesson, UUID> {
}
