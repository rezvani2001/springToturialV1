package com.example.demo.Controllers.v1;

import com.example.demo.Models.Lesson;
import com.example.demo.Models.Person;
import com.example.demo.Models.Student;
import com.example.demo.Models.Teacher;
import com.example.demo.Repositories.PersonRepository;
import com.example.demo.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {
    private final TeacherRepository repository;
    private final PersonRepository personRepository;

    /**
     * constructor to initialize the class
     *
     * @param repository       instance of {@link TeacherRepository} that is maid and injected by spring
     * @param personRepository instance of {@link PersonRepository} that is maid and injected by spring( needed because teachers ar person as well)
     */
    public TeacherController(@Autowired TeacherRepository repository, @Autowired PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    /**
     * method to inset a new teacher in DB
     * it checks if the national code is already exist
     * then makes the person first and gives it to teacher
     *
     * @param person        the actual teacher( identity and this stuff)
     * @param bindingResult the result of validating the given person
     * @param teacher       the teacher to insert it in db, it just injected by spring
     * @param map           an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return the result of inserting teacher
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Map<String, Object>> newTeacher(@RequestBody @Valid Person person, BindingResult bindingResult,
                                                          @Autowired Teacher teacher, @Autowired LinkedHashMap<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("status", "failed");
            map.put(bindingResult.getFieldError().getField(), bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(map);
        } else {
            if (personRepository.findPersonByNationalKey(person.getNationalKey()).isPresent()) {
                map.put("status", "failed");
                map.put("name", "national code already exists");
                return ResponseEntity.badRequest().body(map);
            } else {
                map.put("status", "success");
                person.setId(UUID.randomUUID());
                teacher.setId(UUID.randomUUID());
                personRepository.save(person);
                teacher.setPerson(person);
                repository.save(teacher);
                map.put("status", "success");
                return ResponseEntity.ok(map);
            }
        }
    }

    @RequestMapping(path = "lesson/{id}")
    public Set<Lesson> getLessons(@PathVariable UUID id) {
        return repository.findById(id).get().getLessons();
    }

    /**
     * returns all students that got any lesson with the given teacher
     *
     * @param teacherID id of teacher that exists in request path
     * @param map       an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return students of the teacher
     */
    @RequestMapping(method = RequestMethod.GET, path = "student/{teacherID}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getStudents(@PathVariable UUID teacherID,
                                                           @Autowired LinkedHashMap<String, Object> map) {
        Optional<Teacher> optionalTeacher = repository.findById(teacherID);
        if (optionalTeacher.isPresent()) {
            ArrayList<Student> students = new ArrayList<>();

            optionalTeacher.get().getLessons().forEach(lesson -> {
                lesson.getStudents().forEach(binding -> {
                    students.add(binding.getStudent());
                });
            });

            map.put("status", "success");
            map.put("students",students);
            return ResponseEntity.ok(map);
        } else {
            map.put("status", "failed");
            map.put("message", "no teacher found with provided id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
    }


    /**
     * get all teachers
     *
     * @param map an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return list of existing teachers
     */
    @RequestMapping(produces = "application/json")
    public ResponseEntity<Map<String, Object>> getTeachers(@Autowired LinkedHashMap<String, Object> map) {
        map.put("status", "success");
        map.put("teachers", repository.findAll());
        return ResponseEntity.ok(map);
    }
}
