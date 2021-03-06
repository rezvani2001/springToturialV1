package com.example.demo.Controllers.v1;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.*;
import com.example.demo.Models.messages.CollegeMessages;
import com.example.demo.Models.messages.MessageInterpreter;
import com.example.demo.Models.responseModels.PayloadResponse;
import com.example.demo.services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Payload;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "api/v1/college")
public class CollegeController {
    private final CollegeService collegeService;

    public CollegeController(@Autowired CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    /**
     * methode to create a new college
     *
     * @param college the college that client wants to generate on server
     * @return the response of client request to it as a json
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> newCollege(@Valid @RequestBody College college, WebRequest webRequest) throws GeneralException {
        collegeService.insertCollege(college);
        return MessageInterpreter.getDesiredResponse(CollegeMessages.ADDED, webRequest);
    }

    /**
     * method to assign a student to a college
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @return result of adding asked student to asked college
     */
    @RequestMapping(path = "assign/student", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignStudent(@RequestBody HashMap<String, UUID> request, WebRequest webRequest) throws GeneralException {
        UUID collegeId = request.get("collegeId");
        UUID studentId = request.get("studentId");

        if (collegeId == null || studentId == null) {
            throw new GeneralException(CollegeMessages.REQUIRED_STDID_CLGID);
        }

        collegeService.assignStudent(studentId, collegeId);
        return MessageInterpreter.getDesiredResponse(CollegeMessages.STUDENT_ASSIGNED, webRequest);
    }

    /**
     * method to assign a teacher to a college
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @return result of adding asked teacher to asked college
     */
    @RequestMapping(path = "assign/teacher", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignTeacher(@RequestBody HashMap<String, UUID> request, WebRequest webRequest) throws GeneralException {
        UUID collegeId = request.get("collegeId");
        UUID teacherId = request.get("teacherId");

        if (collegeId == null || teacherId == null) {
            throw new GeneralException(CollegeMessages.REQUIRED_TEACHERID_CLGID);
        }

        collegeService.assignTeacher(teacherId, collegeId);
        return MessageInterpreter.getDesiredResponse(CollegeMessages.TEACHER_ASSIGNED, webRequest);
    }


    /**
     * method to assign a lesson to a college
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @return result of adding asked lesson to asked college
     */
    @RequestMapping(path = "assign/lesson", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignLesson(@RequestBody HashMap<String, UUID> request, WebRequest webRequest) throws GeneralException {
        UUID collegeId = request.get("collegeId");
        UUID lessonId = request.get("lessonId");

        if (collegeId == null || lessonId == null) {
            throw new GeneralException(CollegeMessages.REQUIRED_LESSONID_CLGID);
        }

        collegeService.assignLesson(lessonId, collegeId);
        return MessageInterpreter.getDesiredResponse(CollegeMessages.LESSON_ASSIGNED, webRequest);
    }


    /**
     * methode to get all colleges records
     *
     * @return all existing colleges
     */
    @RequestMapping(produces = "application/json")
    public ResponseEntity<Object> getColleges() {
        return ResponseEntity.ok(new PayloadResponse(collegeService.getAllColleges()));
    }
}
