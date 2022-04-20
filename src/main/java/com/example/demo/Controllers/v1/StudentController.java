package com.example.demo.Controllers.v1;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.Person;
import com.example.demo.Models.messages.MessageInterpreter;
import com.example.demo.Models.messages.StudentMessages;
import com.example.demo.Models.responseModels.PayloadResponse;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * constructor to initialize the class
     */
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    public StudentController() {
    }

    /**
     * method to inset a new student in DB
     * it checks if the national code is already exist
     * then makes the person first and gives it to student
     *
     * @param person the actual student( identity and this stuff)
     * @return the result of inserting student
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> newStudent(@RequestBody @Valid Person person, WebRequest webRequest) throws GeneralException {
        studentService.insertStudent(person);
        return MessageInterpreter.getDesiredResponse(StudentMessages.ADDED, webRequest);
    }

    /**
     * method to get students avg with his uuid
     *
     * @param studentId the id of the asked user
     * @return the avg of the student
     */
    @RequestMapping(path = "{studentId}/avg", produces = "application/json")
    public ResponseEntity<Object> getStudentAVG(@PathVariable UUID studentId) throws GeneralException {
        return ResponseEntity.ok(new PayloadResponse(studentService.getStudentAVG(studentId)));
    }

    /**
     * get all students
     *
     * @return list of existing students
     */
    @RequestMapping(produces = "application/json")
    public ResponseEntity<Object> getStudents() {
        return ResponseEntity.ok(new PayloadResponse(studentService.getAllStudents()));
    }
}
