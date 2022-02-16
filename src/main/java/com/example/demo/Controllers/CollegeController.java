package com.example.demo.Controllers;

import com.example.demo.Models.College;
import com.example.demo.Repositories.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    /**
     * constructor to initialize the field repository of the class
     *
     * @param repository a {@link CollegeRepository} that made by spring framework and injects it in the class
     */
    public CollegeController(@Autowired CollegeRepository repository) {
        this.repository = repository;
    }

    /**
     * methode to create a new college
     *
     * @param college       the college that client wants to generate on server
     * @param bindingResult the result of validating the given college
     * @param map an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return the response of client request to it as a json
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> newCollege(@Valid @RequestBody @NotNull College college,
                                          BindingResult bindingResult, @Autowired LinkedHashMap<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("status", "failed");
            map.put(bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
        } else {
            if (repository.findCollegeByName(college.getName()).isPresent()) {
                map.put("status", "failed");
                map.put("name", "college name already exists");
            }
            college.setId(UUID.randomUUID());
            repository.save(college);
            map.put("status", "success");
        }
        return map;
    }

    /**
     * methode to get all colleges records
     *
     * @param map an instance of {@link LinkedHashMap} injected by spring to use as response
     * @return all existing colleges
     */
    @RequestMapping(produces = "application/json")
    public Map<String, Object> getColleges(@Autowired LinkedHashMap<String, Object> map) {
        map.put("status", "success");
        map.put("colleges", repository.findAll());
        return map;
    }
}
