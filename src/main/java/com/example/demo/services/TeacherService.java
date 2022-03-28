package com.example.demo.services;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.*;
import com.example.demo.Models.messages.TeacherMessages;
import com.example.demo.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final PersonService personService;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, PersonService personService) {
        this.teacherRepository = teacherRepository;
        this.personService = personService;
    }

    ////////////////////////////////////////////////////////////////////////////

    public void insertTeacher(Person person) throws GeneralException {
        personService.insertPerson(person);

        Teacher teacher = new Teacher();
        teacher.setId(UUID.randomUUID());
        teacher.setPerson(person);

        teacherRepository.save(teacher);
    }

    public Set<Lesson> getLessons(UUID teacherId) throws GeneralException {
        return this.getTeacherById(teacherId).getLessons();
    }

    public List<Student> getStudents(UUID teacherId) throws GeneralException {
        Teacher teacher = this.getTeacherById(teacherId);
        List<Student> students = new ArrayList<>();

        teacher.getLessons().forEach(lesson -> {
            lesson.getStudents().forEach(binding -> {
                students.add(binding.getStudent());
            });
        });

        return students;
    }

    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////

    public void grantCollege(Teacher teacher, College college) {
        teacher.setCollege(college);
        teacherRepository.save(teacher);
    }

    public void grantLesson(Teacher teacher, Lesson lesson) {
        teacher.getLessons().add(lesson);
        teacherRepository.save(teacher);
    }

    ////////////////////////////////////////////////////////////////////////////

    public Teacher getTeacherById(UUID teacherId) throws GeneralException {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);

        if (teacher.isPresent()) {
            return teacher.get();
        } else {
            throw new GeneralException(TeacherMessages.NOT_FOUND);
        }
    }

    public List<Teacher> getAllTeachers() {
        return (List<Teacher>) teacherRepository.findAll();
    }
}
