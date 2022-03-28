package com.example.demo.Controllers.v1;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.Person;
import com.example.demo.Models.messages.MessageInterpreter;
import com.example.demo.Models.messages.TeacherMessages;
import com.example.demo.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    /**
     * constructor to initialize the class
     */
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * method to inset a new teacher in DB
     * it checks if the national code is already exist
     * then makes the person first and gives it to teacher
     *
     * @param person the actual teacher( identity and this stuff)
     * @return the result of inserting teacher
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> newTeacher(@RequestBody @Valid Person person, WebRequest webRequest) throws GeneralException {
        teacherService.insertTeacher(person);
        return MessageInterpreter.getDesiredResponse(TeacherMessages.ADDED, webRequest);
    }

    @RequestMapping(path = "lesson/{id}")
    public ResponseEntity<Object> getLessons(@PathVariable UUID id) throws GeneralException {
        // todo change return type
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    /**
     * returns all students that got any lesson with the given teacher
     *
     * @param teacherID id of teacher that exists in request path
     * @return students of the teacher
     */
    @RequestMapping(method = RequestMethod.GET, path = "student/{teacherID}", produces = "application/json")
    public ResponseEntity<Object> getStudents(@PathVariable UUID teacherID) throws GeneralException {
        // todo change return type
        return ResponseEntity.ok(teacherService.getStudents(teacherID));
    }


    /**
     * get all teachers
     *
     * @return list of existing teachers
     */
    @RequestMapping(produces = "application/json")
    public ResponseEntity<Object> getTeachers() {
        // todo change return type
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }
}
