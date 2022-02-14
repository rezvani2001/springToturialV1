package com.example.demo.Controllers;

import com.example.demo.Models.Lesson;
import com.example.demo.Repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/lesson")
public class LessonController {
    private final LessonRepository repository;

    public LessonController(@Autowired LessonRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String newLesson(@RequestBody @Valid Lesson lesson, BindingResult bindingResult) {
        // todo: imp
        return "";
    }
}
