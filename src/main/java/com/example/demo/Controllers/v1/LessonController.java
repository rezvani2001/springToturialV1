package com.example.demo.Controllers.v1;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.Lesson;
import com.example.demo.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "api/v1/lesson")
public class LessonController {
    private final LessonService lessonService;


    public LessonController(@Autowired LessonService lessonService) {
        this.lessonService = lessonService;
    }

    /**
     * method to insert a new lesson into the DB
     *
     * @param lesson        the lesson to be added
     * @param bindingResult result of validating the given lesson
     * @param map           an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return the result of inserting the lesson
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> newLesson(@RequestBody @Valid Lesson lesson, BindingResult bindingResult,
                                                         @Autowired LinkedHashMap<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("status", "failed");
            map.put(bindingResult.getFieldError().getField(), bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(map);
        } else {
            lessonService.insertLesson(lesson);
            map.put("status", "success");
            return ResponseEntity.ok(map);
        }
    }

    /**
     * method to assign a teacher to a lesson
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @param map     an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return result of adding asked teacher to asked lesson
     */
    @RequestMapping(path = "assign/teacher", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignTeacher(@RequestBody HashMap<String, UUID> request,
                                                @Autowired LinkedHashMap<String, Object> map) {
        UUID lessonId = request.get("lessonId");
        UUID teacherId = request.get("teacherId");

        if (lessonId == null || teacherId == null) {
            map.put("status", "failed");
            map.put("message", "lessonId and teacherId are required");
            return ResponseEntity.badRequest().body(map);
        }

        try {
            lessonService.assignTeacher(teacherId, lessonId);
            return ResponseEntity.ok("added");
        } catch (GeneralException e) {
            return e.getEnResponse();
        }
    }

    /**
     * method to assign a student to a lesson
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @param map     an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return result of adding asked student to asked lesson
     */
    @RequestMapping(path = "assign/student", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> assignStudent(@RequestBody HashMap<String, UUID> request,
                                                @Autowired LinkedHashMap<String, Object> map) {
        UUID lessonId = request.get("lessonId");
        UUID studentId = request.get("studentId");

        if (lessonId == null || studentId == null) {
            map.put("status", "failed");
            map.put("message", "lessonId and studentId are required");
            return ResponseEntity.badRequest().body(map);
        }

        try {
            lessonService.assignStudent(studentId, lessonId);
            return ResponseEntity.ok("added");
        } catch (GeneralException e) {
            return e.getEnResponse();
        }
    }

    /**
     * calculates avg of students in the lesson
     *
     * @param lessonId id of the lesson
     * @param map      an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return overall avg of lesson
     */
    @RequestMapping(path = "avg/{lessonId}")
    public ResponseEntity<Object> getStudentsAVG(@PathVariable UUID lessonId,
                                                 @Autowired LinkedHashMap<String, Object> map) {
        try {
            double grade = lessonService.getAvgOfStudents(lessonId);
            return ResponseEntity.ok(grade);
        } catch (GeneralException e) {
            return e.getEnResponse();
        }
    }

    /**
     * method to grade a student in class
     *
     * @param request the request from user( containing uuid for ids and double for grade)
     * @param map     an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return the result of setting grade
     */
    @RequestMapping(path = "student/grade", produces = "application/json")
    public ResponseEntity<Object> gradeStudent(@RequestBody Map<String, Object> request,
                                               @Autowired LinkedHashMap<String, Object> map) {
        UUID lessonId = (UUID) request.get("lessonId");
        UUID studentId = (UUID) request.get("studentId");
        Float grade = (Float) request.get("grade");

        if (lessonId == null || studentId == null || grade == null) {
            return ResponseEntity.badRequest().body("lessonId and studentId and grade are required");
        }

        try {
            lessonService.gradeStudent(lessonId, studentId, grade);
            return ResponseEntity.ok("done");
        } catch (GeneralException e) {
            return e.getEnResponse();
        }
    }


    /**
     * get all lessons
     *
     * @param map an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return list of existing lessons
     */
    @RequestMapping(produces = "application/json")
    public ResponseEntity<Object> getStudents(@Autowired LinkedHashMap<String, Object> map) {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }
}
