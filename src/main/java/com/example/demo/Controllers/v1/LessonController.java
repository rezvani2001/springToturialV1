package com.example.demo.Controllers.v1;

import com.example.demo.Models.Lesson;
import com.example.demo.Models.Student;
import com.example.demo.Models.StudentLesson;
import com.example.demo.Models.Teacher;
import com.example.demo.Repositories.LessonRepository;
import com.example.demo.Repositories.StudentLessonRepository;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "api/v1/lesson")
public class LessonController {
    private final LessonRepository repository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final StudentLessonRepository studentLessonRepository;

    /**
     * constructor to initialize the class
     *
     * @param repository              instance of {@link LessonRepository} that is maid and injected by spring
     * @param teacherRepository       instance of {@link TeacherRepository} that is maid and injected by spring( for the further use)
     * @param studentRepository       instance of {@link StudentRepository} that is maid and injected by spring( for the further use)
     * @param studentLessonRepository instance of {@link StudentLessonRepository} that is maid and injected by spring( for the further use)
     */
    public LessonController(@Autowired LessonRepository repository, TeacherRepository teacherRepository, StudentRepository studentRepository, StudentLessonRepository studentLessonRepository) {
        this.repository = repository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.studentLessonRepository = studentLessonRepository;
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
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @param map     an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return result of adding asked teacher to asked lesson
     */
    @RequestMapping(path = "assign/teacher", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> assignTeacher(@RequestBody HashMap<String, UUID> request,
                                                             @Autowired LinkedHashMap<String, Object> map) {
        UUID lessonId = request.get("lessonId");
        UUID teacherId = request.get("teacherId");

        if (lessonId == null || teacherId == null) {
            map.put("status", "failed");
            map.put("message", "lessonId and teacherId are required");
            return ResponseEntity.badRequest().body(map);
        }

        Optional<Lesson> optionalLesson = repository.findById(lessonId);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();
            Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
            if (optionalTeacher.isPresent()) {
                Teacher teacher = optionalTeacher.get();

                lesson.getTeachers().add(teacher);
                teacher.getLessons().add(lesson);

                teacherRepository.save(teacher);
                repository.save(lesson);

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
     * @param request the user request( it's map because it's not possible to deserialize UUID from this format)
     * @param map     an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return result of adding asked student to asked lesson
     */
    @RequestMapping(path = "assign/student", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> assignStudent(@RequestBody HashMap<String, UUID> request,
                                                             @Autowired LinkedHashMap<String, Object> map) {
        UUID lessonId = request.get("lessonId");
        UUID studentId = request.get("studentId");

        if (lessonId == null || studentId == null) {
            map.put("status", "failed");
            map.put("message", "lessonId and studentId are required");
            return ResponseEntity.badRequest().body(map);
        }

        Optional<Lesson> optionalLesson = repository.findById(lessonId);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();
                StudentLesson studentLesson = new StudentLesson(student, lesson);

                studentLessonRepository.save(studentLesson);

                lesson.getStudents().add(studentLesson);
                student.getStudentLessons().add(studentLesson);

                studentRepository.save(student);
                repository.save(lesson);

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
     * calculates avg of students in the lesson
     *
     * @param lessonId id of the lesson
     * @param map      an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return overall avg of lesson
     */
    @RequestMapping(path = "avg/{lessonId}")
    public ResponseEntity<Map<String, Object>> getStudentsAVG(@PathVariable UUID lessonId,
                                                              @Autowired LinkedHashMap<String, Object> map) {
        Optional<Lesson> optionalLesson = repository.findById(lessonId);

        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();
            double grade = lesson.getStudents().stream().mapToDouble(StudentLesson::getGrade).sum();

            map.put("status", "success");
            map.put("average", grade / lesson.getStudents().size());
            return ResponseEntity.ok(map);
        } else {
            map.put("status", "failed");
            map.put("message", "lesson not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
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
    public ResponseEntity<Map<String, Object>> gradeStudent(@RequestBody Map<String, Object> request,
                                                            @Autowired LinkedHashMap<String, Object> map) {
        try {

            UUID lessonId = (UUID) request.get("lessonId");
            UUID studentId = (UUID) request.get("studentId");
            double grade = (Double) request.get("grade");

            if (lessonId == null || studentId == null) {
                map.put("status", "failed");
                map.put("message", "lessonId and studentId are required");
                return ResponseEntity.badRequest().body(map);
            }

            Optional<Lesson> optionalLesson = repository.findById(lessonId);
            if (optionalLesson.isPresent()) {
                Optional<Student> optionalStudent = studentRepository.findById(studentId);

                if (optionalStudent.isPresent()) {
                    Lesson lesson = optionalLesson.get();
                    Student student = optionalStudent.get();

                    for (StudentLesson studentLesson : lesson.getStudents()) {
                        if (studentLesson.getStudent() == student) {
                            studentLesson.setGrade((float) grade);
                            studentLessonRepository.save(studentLesson);

                            map.put("status", "success");
                            return ResponseEntity.ok(map);
                        }
                    }

                    map.put("status", "failed");
                    map.put("message", "student does not belong to the provided lesson");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
                } else {
                    map.put("status", "failed");
                    map.put("message", "student not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
                }
            } else {
                map.put("status", "failed");
                map.put("message", "lesson not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            }
        } catch (Exception e) {
            map.put("status", "failed");
            map.put("message", "provided information is invalid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
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
