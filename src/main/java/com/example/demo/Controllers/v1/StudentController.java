package com.example.demo.Controllers.v1;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.Person;
import com.example.demo.Models.messages.StudentMessages;
import com.example.demo.Models.responseModels.Response;
import com.example.demo.Models.responseModels.Status;
import com.example.demo.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;

    /**
     * constructor to initialize the class
     */
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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
    public ResponseEntity<Object> newStudent(@RequestBody @Valid Person person) throws GeneralException {
        studentService.insertStudent(person);
        return ResponseEntity.ok(new Response(Status.SUCCESS, StudentMessages.ADDED.getEnMessage()));
    }

    /**
     * method to get students avg with his uuid
     *
     * @param studentId the id of the asked user
     * @return the avg of the student
     */
    @RequestMapping(path = "avg/{studentId}", produces = "application/json")
    public ResponseEntity<Object> getStudentAVG(@PathVariable UUID studentId) throws GeneralException {
        return ResponseEntity.ok(new Response(Status.SUCCESS, studentService.getStudentAVG(studentId)));
    }

    /**
     * get all students
     *
     * @return list of existing students
     */
    @RequestMapping(produces = "application/json")
    public ResponseEntity<Object> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
}
