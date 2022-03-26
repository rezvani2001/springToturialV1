package com.example.demo.Controllers.v1;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.*;
import com.example.demo.Models.messages.CollegeMEssages;
import com.example.demo.Models.responseModels.Response;
import com.example.demo.Models.responseModels.Status;
import com.example.demo.services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param college       the college that client wants to generate on server
     * @return the response of client request to it as a json
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> newCollege(@Valid @RequestBody College college) throws GeneralException {
        collegeService.insertCollege(college);
        return ResponseEntity.ok(new Response(Status.SUCCESS, CollegeMEssages.ADDED.getEnMessage()));
    }

    /**
     * method to assign a student to a college
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @return result of adding asked student to asked college
     */
    @RequestMapping(path = "assign/student", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignStudent(@RequestBody HashMap<String, UUID> request) throws GeneralException {
        UUID collegeId = request.get("collegeId");
        UUID studentId = request.get("studentId");

        if (collegeId == null || studentId == null) {
            throw new GeneralException(CollegeMEssages.REQUIRED_STDID_CLGID);
        }

        collegeService.assignStudent(studentId, collegeId);
        return ResponseEntity.ok("added");
    }

    /**
     * method to assign a teacher to a college
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @return result of adding asked teacher to asked college
     */
    @RequestMapping(path = "assign/teacher", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignTeacher(@RequestBody HashMap<String, UUID> request) throws GeneralException {
        UUID collegeId = request.get("collegeId");
        UUID teacherId = request.get("teacherId");

        if (collegeId == null || teacherId == null) {
            throw new GeneralException(CollegeMEssages.REQUIRED_TEACHERID_CLGID);
        }

        collegeService.assignTeacher(teacherId, collegeId);
        return ResponseEntity.ok("added");
    }


    /**
     * method to assign a lesson to a college
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @return result of adding asked lesson to asked college
     */
    @RequestMapping(path = "assign/lesson", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignLesson(@RequestBody HashMap<String, UUID> request) throws GeneralException {
        UUID collegeId = request.get("collegeId");
        UUID lessonId = request.get("lessonId");

        if (collegeId == null || lessonId == null) {
            throw new GeneralException(CollegeMEssages.REQUIRED_LESSONID_CLGID);
        }

        collegeService.assignLesson(lessonId, collegeId);
        return ResponseEntity.ok("added");
    }


    /**
     * methode to get all colleges records
     *
     * @param map an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return all existing colleges
     */
    @RequestMapping(produces = "application/json")
    public ResponseEntity<Map<String, Object>> getColleges(@Autowired LinkedHashMap<String, Object> map) {
        map.put("status", "success");
        map.put("colleges", collegeService.getAllColleges());
        return ResponseEntity.ok(map);
    }
}
