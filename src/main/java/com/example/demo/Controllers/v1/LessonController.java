package com.example.demo.Controllers.v1;

import com.example.demo.Models.Lesson;
import com.example.demo.Models.Student;
import com.example.demo.Models.StudentLesson;
import com.example.demo.Models.Teacher;
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
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/lesson")
public class LessonController {
    private final LessonRepository repository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    /**
     * constructor to initialize the class
     *
     * @param repository        instance of {@link LessonRepository} that is maid and injected by spring
     * @param teacherRepository instance of {@link TeacherRepository} that is maid and injected by spring( for the further use)
     * @param studentRepository instance of {@link StudentRepository} that is maid and injected by spring( for the further use)
     */
    public LessonController(@Autowired LessonRepository repository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.repository = repository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
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
    public ResponseEntity<Map<String, Object>> newLesson(@RequestBody @Valid Lesson lesson, BindingResult bindingResult,
                                                         @Autowired LinkedHashMap<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("status", "failed");
            map.put(bindingResult.getFieldError().getField(), bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(map);
        } else {
            lesson.setId(UUID.randomUUID());
            repository.save(lesson);
            map.put("status", "success");
            return ResponseEntity.ok(map);
        }
    }

    /**
     * method to assign a teacher to a lesson
     *
     * @param lessonId  the id of lesson that want to add the teacher to it
     * @param teacherId the id of teacher that want to be added to the lesson
     * @param map       an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return result of adding asked teacher to asked lesson
     */
    @RequestMapping(path = "assign/teacher", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> assignTeacher(@RequestBody UUID lessonId, @RequestBody UUID teacherId,
                                                             @Autowired LinkedHashMap<String, Object> map) {
        Optional<Lesson> optionalLesson = repository.findById(lessonId);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();

            Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
            if (optionalTeacher.isPresent()) {
                lesson.getTeachers().add(optionalTeacher.get());
                map.put("status", "success");
                map.put("message", "teacher has been assigned to class");
                return ResponseEntity.ok(map);
            } else {
                map.put("status", "failed");
                map.put("teacher_id", "no teacher found with that id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            }
        } else {
            map.put("status", "failed");
            map.put("lesson_id", "no lesson found with that id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
    }

    /**
     * method to assign a student to a lesson
     *
     * @param lessonId  the id of lesson that want to add the student to it
     * @param studentId the id of student that want to be added to the lesson
     * @param map       an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return result of adding asked student to asked lesson
     */
    @RequestMapping(path = "assign/student", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> assignStudent(@RequestBody UUID lessonId, @RequestBody UUID studentId,
                                             @Autowired LinkedHashMap<String, Object> map) {
        Optional<Lesson> optionalLesson = repository.findById(lessonId);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()) {
                lesson.getStudents().add(new StudentLesson(optionalStudent.get(), lesson));
                map.put("status", "success");
                map.put("message", "student has been assigned to class");
                return ResponseEntity.ok(map);
            } else {
                map.put("status", "failed");
                map.put("student_id", "no student found with that id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            }
        } else {
            map.put("status", "failed");
            map.put("lesson_id", "no lesson found with that id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
    }

    /**
     * get all lessons
     *
     * @param map an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return list of existing lessons
     */
    @RequestMapping(produces = "application/json")
    public ResponseEntity<Map<String, Object>> getStudents(@Autowired LinkedHashMap<String, Object> map) {
        map.put("status", "success");
        map.put("lessons", repository.findAll());
        return ResponseEntity.ok(map);
    }
}
