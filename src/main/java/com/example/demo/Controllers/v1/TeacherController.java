package com.example.demo.Controllers.v1;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.Person;
import com.example.demo.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
     * @param person        the actual teacher( identity and this stuff)
     * @param bindingResult the result of validating the given person
     * @return the result of inserting teacher
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> newTeacher(@RequestBody @Valid Person person, BindingResult bindingResult) {
        try {
            teacherService.insertTeacher(person);
            return ResponseEntity.ok("done");
        } catch (GeneralException e) {
            return e.getEnResponse();
        }
    }

    @RequestMapping(path = "lesson/{id}")
    public ResponseEntity<Object> getLessons(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(teacherService.getTeacherById(id));
        } catch (GeneralException e){
            return e.getEnResponse();
        }
    }

    /**
     * returns all students that got any lesson with the given teacher
     *
     * @param teacherID id of teacher that exists in request path
     * @return students of the teacher
     */
    @RequestMapping(method = RequestMethod.GET, path = "student/{teacherID}", produces = "application/json")
    public ResponseEntity<Object> getStudents(@PathVariable UUID teacherID) {
        try {
            return ResponseEntity.ok(teacherService.getStudents(teacherID));
        } catch (GeneralException e) {
            return e.getEnResponse();
        }
    }


    /**
     * get all teachers
     *
     * @return list of existing teachers
     */
    @RequestMapping(produces = "application/json")
    public ResponseEntity<Object> getTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }
}
