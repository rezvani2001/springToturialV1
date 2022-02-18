package com.example.demo.Controllers.v1;

import com.example.demo.Models.Person;
import com.example.demo.Models.Student;
import com.example.demo.Repositories.PersonRepository;
import com.example.demo.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentRepository repository;
    private final PersonRepository personRepository;

    /**
     * constructor to initialize the class
     *
     * @param repository       instance of {@link StudentRepository} that is maid and injected by spring
     * @param personRepository instance of {@link PersonRepository} that is maid and injected by spring( needed because students ar person as well)
     */
    public StudentController(@Autowired StudentRepository repository, @Autowired PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    /**
     * method to inset a new student in DB
     * it checks if the national code is already exist
     * then makes the person first and gives it to student
     *
     * @param person        the actual student( identity and this stuff)
     * @param bindingResult the result of validating the given person
     * @param student       the student to insert i db, it just injected by spring
     * @param map           an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return the result of inserting student
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Map<String, Object>> newStudent(@RequestBody @Valid Person person, BindingResult bindingResult,
                                                          @Autowired Student student, @Autowired LinkedHashMap<String, Object> map) {
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
                person.setId(UUID.randomUUID());
                student.setId(UUID.randomUUID());
                personRepository.save(person);
                student.setPerson(person);
                repository.save(student);
                map.put("status", "success");
                return ResponseEntity.ok(map);
            }
        }
    }

    /**
     * get all students
     *
     * @param map an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return list of existing students
     */
    @RequestMapping(produces = "application/json")
    public  ResponseEntity<Map<String, Object>> getStudents(@Autowired LinkedHashMap<String, Object> map) {
        map.put("status", "success");
        map.put("students",repository.findAll());
        return ResponseEntity.ok(map);
    }
}
