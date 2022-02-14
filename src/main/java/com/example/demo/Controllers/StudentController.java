package com.example.demo.Controllers;

import com.example.demo.Models.Person;
import com.example.demo.Models.Student;
import com.example.demo.Repositories.PersonRepository;
import com.example.demo.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentRepository repository;
    private final PersonRepository personRepository;

    public StudentController(@Autowired StudentRepository repository,@Autowired PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String newStudent(@RequestBody @Valid Person person, BindingResult bindingResult, @Autowired Student student) {
        if (bindingResult.hasErrors()) {
            return "{\"status\" : \"failed\"," +
                    "\"" + bindingResult.getFieldError().getField() +
                    "\": \"" + bindingResult.getFieldError().getDefaultMessage() + "\"}";
        } else {
            if (personRepository.findPersonByNationalKey(person.getNationalKey()).isPresent()) {
                return "{" +
                        "\"status\" : \"failed\"," +
                        "\"name\" : \"national code already exists\"" +
                        "}";
            }
            person.setId(UUID.randomUUID());
            student.setId(UUID.randomUUID());
            personRepository.save(person);
            student.setPerson(person);
            repository.save(student);
            return "{\"status\" : \"success\"";
        }
    }

    @RequestMapping(produces = "application/json")
    public Iterable<Student> getStudents() {
        return repository.findAll();
    }
}
