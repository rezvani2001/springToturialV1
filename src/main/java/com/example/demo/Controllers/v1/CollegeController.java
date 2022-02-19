package com.example.demo.Controllers.v1;

import com.example.demo.Models.*;
import com.example.demo.Repositories.CollegeRepository;
import com.example.demo.Repositories.LessonRepository;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final CollegeRepository repository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;


    /**
     * constructor to initialize the field repository of the class
     *
     * @param repository        a {@link CollegeRepository} that made by spring framework and injects it in the class
     * @param studentRepository instance of {@link StudentRepository} that made by spring framework and injects it in the class( required further use)
     * @param teacherRepository instance of {@link TeacherRepository} that made by spring framework and injects it in the class( required further use)
     * @param lessonRepository  instance of {@link LessonRepository} that made by spring framework and injects it in the class( required further use)
     */
    public CollegeController(@Autowired CollegeRepository repository, @Autowired StudentRepository studentRepository,
                             @Autowired TeacherRepository teacherRepository, @Autowired LessonRepository lessonRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.lessonRepository = lessonRepository;
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
    public ResponseEntity<Map<String, Object>> newCollege(@Valid @RequestBody @NotNull College college,
                                                          BindingResult bindingResult, @Autowired LinkedHashMap<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("status", "failed");
            map.put(bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(map);
        } else {
            if (repository.findCollegeByName(college.getName()).isPresent()) {
                map.put("status", "failed");
                map.put("name", "college name already exists");
                return ResponseEntity.badRequest().body(map);
            }
            college.setId(UUID.randomUUID());
            repository.save(college);
            map.put("status", "success");
            return ResponseEntity.ok(map);
        }
    }

    /**
     * method to assign a student to a college
     *
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format) * @param map       an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return result of adding asked student to asked college
     */
    @RequestMapping(path = "assign/student", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> assignStudent(@RequestBody HashMap<String, UUID> request,
                                                             @Autowired LinkedHashMap<String, Object> map) {
        UUID collegeId = request.get("collegeId");
        UUID studentId = request.get("studentId");

        if (collegeId == null || studentId == null) {
            map.put("status", "failed");
            map.put("message", "collegeId and studentId are required");
            return ResponseEntity.badRequest().body(map);
        }

        Optional<College> optionalCollege = repository.findById(collegeId);
        if (optionalCollege.isPresent()) {
            College college = optionalCollege.get();

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();

                college.getStudents().add(student);
                student.setCollege(college);

                repository.save(college);
                studentRepository.save(student);

                map.put("status", "success");
                map.put("message", "student has been assigned to college");
                return ResponseEntity.ok(map);
            } else {
                map.put("status", "failed");
                map.put("student_id", "no student found with that id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            }
        } else {
            map.put("status", "failed");
            map.put("college_id", "no college found with that id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
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
    public ResponseEntity<Map<String, Object>> assignTeacher(@RequestBody HashMap<String, UUID> request,
                                                             @Autowired LinkedHashMap<String, Object> map) {
        UUID collegeId = request.get("collegeId");
        UUID teacherId = request.get("teacherId");

        if (collegeId == null || teacherId == null) {
            map.put("status", "failed");
            map.put("message", "collegeId and teacherId are required");
            return ResponseEntity.badRequest().body(map);
        }

        Optional<College> optionalCollege = repository.findById(collegeId);
        if (optionalCollege.isPresent()) {
            College college = optionalCollege.get();

            Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
            if (optionalTeacher.isPresent()) {
                Teacher teacher = optionalTeacher.get();
                college.getTeachers().add(teacher);

                optionalTeacher.get().setCollege(college);
                teacher.setCollege(college);

                repository.save(college);
                teacherRepository.save(teacher);

                map.put("status", "success");
                map.put("message", "teacher has been assigned to college");
                return ResponseEntity.ok(map);
            } else {
                map.put("status", "failed");
                map.put("teacher_id", "no teacher found with that id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            }
        } else {
            map.put("status", "failed");
            map.put("college_id", "no college found with that id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
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
    public ResponseEntity<Map<String, Object>> assignLesson(@RequestBody HashMap<String, UUID> request,
                                                            @Autowired LinkedHashMap<String, Object> map) {
        UUID collegeId = request.get("collegeId");
        UUID lessonId = request.get("lessonId");

        if (collegeId == null || lessonId == null) {
            map.put("status", "failed");
            map.put("message", "collegeId and lessonId are required");
            return ResponseEntity.badRequest().body(map);
        }

        Optional<College> optionalCollege = repository.findById(collegeId);
        if (optionalCollege.isPresent()) {
            College college = optionalCollege.get();

            Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
            if (optionalLesson.isPresent()) {
                college.getLessons().add(optionalLesson.get());
                repository.save(college);
                map.put("status", "success");
                map.put("message", "lesson has been assigned to college");
                return ResponseEntity.ok(map);
            } else {
                map.put("status", "failed");
                map.put("lesson_id", "no lesson found with that id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            }
        } else {
            map.put("status", "failed");
            map.put("college_id", "no college found with that id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
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
        map.put("colleges", repository.findAll());
        return ResponseEntity.ok(map);
    }
}
