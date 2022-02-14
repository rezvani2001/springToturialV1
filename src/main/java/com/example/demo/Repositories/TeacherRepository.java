package com.example.demo.Repositories;

import com.example.demo.Models.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TeacherRepository extends CrudRepository<Teacher, UUID> {
}
