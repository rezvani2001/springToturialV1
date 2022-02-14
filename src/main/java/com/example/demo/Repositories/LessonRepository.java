package com.example.demo.Repositories;

import com.example.demo.Models.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LessonRepository extends CrudRepository<Lesson, UUID> {
}
