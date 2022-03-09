package com.example.demo.Controllers.v1;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.*;
import com.example.demo.Models.messages.CollegeMEssages;
import com.example.demo.Models.responseModels.Response;
import com.example.demo.Models.responseModels.Status;
import com.example.demo.services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
     * @param bindingResult the result of validating the given college
     * @param map           an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return the response of client request to it as a json
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> newCollege(@Valid @RequestBody @NotNull College college,
                                             BindingResult bindingResult, @Autowired LinkedHashMap<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("status", "failed");
            map.put(bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(map);
        } else {
            try {
                collegeService.insertCollege(college);
                return ResponseEntity.ok(new Response(Status.SUCCESS, CollegeMEssages.ADDED.getEnMessage()));
            } catch (GeneralException e) {
                return e.getEnResponse();
            }
        }
    }

    /**
     * method to assign a student to a college
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format) * @param map       an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return result of adding asked student to asked college
     */
    @RequestMapping(path = "assign/student", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignStudent(@RequestBody HashMap<String, UUID> request,
                                                @Autowired LinkedHashMap<String, Object> map) {
        UUID collegeId = request.get("collegeId");
        UUID studentId = request.get("studentId");

        if (collegeId == null || studentId == null) {
            map.put("status", "failed");
            map.put("message", "collegeId and studentId are required");
            return ResponseEntity.badRequest().body(map);
        }

        try {
            collegeService.assignStudent(studentId, collegeId);
            return ResponseEntity.ok("added");
        } catch (GeneralException e) {
            return e.getEnResponse();
        }
    }

    /**
     * method to assign a teacher to a college
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @param map     an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return result of adding asked teacher to asked college
     */
    @RequestMapping(path = "assign/teacher", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignTeacher(@RequestBody HashMap<String, UUID> request,
                                                @Autowired LinkedHashMap<String, Object> map) {
        UUID collegeId = request.get("collegeId");
        UUID teacherId = request.get("teacherId");

        if (collegeId == null || teacherId == null) {
            map.put("status", "failed");
            map.put("message", "collegeId and teacherId are required");
            return ResponseEntity.badRequest().body(map);
        }

        try {
            collegeService.assignTeacher(teacherId, collegeId);
            return ResponseEntity.ok("added");
        } catch (GeneralException e) {
            return e.getEnResponse();
        }
    }


    /**
     * method to assign a lesson to a college
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @param map     an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return result of adding asked lesson to asked college
     */
    @RequestMapping(path = "assign/lesson", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignLesson(@RequestBody HashMap<String, UUID> request,
                                               @Autowired LinkedHashMap<String, Object> map) {
        UUID collegeId = request.get("collegeId");
        UUID lessonId = request.get("lessonId");

        if (collegeId == null || lessonId == null) {
            map.put("status", "failed");
            map.put("message", "collegeId and lessonId are required");
            return ResponseEntity.badRequest().body(map);
        }

        try {
            collegeService.assignLesson(lessonId, collegeId);
            return ResponseEntity.ok("added");
        } catch (GeneralException e) {
            return e.getEnResponse();
        }
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
