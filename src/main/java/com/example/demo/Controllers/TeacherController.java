package com.example.demo.Controllers;

import com.example.demo.Models.Person;
import com.example.demo.Models.Teacher;
import com.example.demo.Repositories.PersonRepository;
import com.example.demo.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {
    private final TeacherRepository repository;
    private final PersonRepository personRepository;

    public TeacherController(@Autowired TeacherRepository repository,@Autowired PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String newTeacher(@RequestBody @Valid Person person, BindingResult bindingResult,@Autowired Teacher teacher) {
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
            teacher.setId(UUID.randomUUID());
            personRepository.save(person);
            teacher.setPerson(person);
            repository.save(teacher);
            return "{\"status\" : \"success\"";
        }
    }

    @RequestMapping(produces = "application/json")
    public Iterable<Teacher> getTechers() {
        return repository.findAll();
    }
}
