package com.example.demo.Models.responseModels;

import com.example.demo.Models.Lesson;
import com.example.demo.Models.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JacksonJsonTest {

    @Test
    void transfer() throws Exception {
        Person person = new Person();
        person.setFirstname("amir");
        person.setLastname("rezvani");
        person.setNationalKey("4580396987");

        System.out.println(person.toString());

        Person jacked = (Person) JacksonJson.transfer(person.toString(), new Person());

        assertThat(jacked.getNationalKey()).isEqualTo(person.getNationalKey());
        assertThat(jacked.getLastname()).isEqualTo(person.getLastname());
        assertThat(jacked.getFirstname()).isEqualTo(person.getFirstname());
    }

    @Test
    void transferLesson() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setName("computerENG");
        lesson.setUnit(3);

        Lesson jacked = (Lesson) JacksonJson.transfer(lesson.toString(), new Lesson());

        assertThat(jacked.getName()).isEqualTo(lesson.getName());
        assertThat(jacked.getUnit()).isEqualTo(lesson.getUnit());
    }
}