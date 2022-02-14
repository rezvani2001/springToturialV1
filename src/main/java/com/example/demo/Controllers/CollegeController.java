package com.example.demo.Controllers;

import com.example.demo.Models.College;
import com.example.demo.Repositories.CollegeRepository;
import com.example.demo.extras.Mapping;
import com.example.demo.extras.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

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
     * @return the response of client request to it as a json
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Message newCollege(@Valid @RequestBody @NotNull College college,
                              BindingResult bindingResult, @Autowired Message message) {
        if (bindingResult.hasErrors()) {
            message.setStatus(Message.Status.FAILED);
            message.setPayload(new Mapping(bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage()));
        } else {
            if (repository.findCollegeByName(college.getName()).isPresent()) {
                message.setStatus(Message.Status.FAILED);
                message.setPayload(new Mapping("name", "college name already exists"));
            }
            college.setId(UUID.randomUUID());
            repository.save(college);

            message.setStatus(Message.Status.SUCCESS);
        }
        return message;
    }

    @RequestMapping(produces = "application/json")
    public Message getColleges(@Autowired Message message) {
        message.setStatus(Message.Status.SUCCESS);
        message.setPayload(new Mapping("Colleges", repository.findAll()));
        return message;
    }
}
