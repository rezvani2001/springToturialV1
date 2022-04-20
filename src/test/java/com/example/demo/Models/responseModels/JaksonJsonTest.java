package com.example.demo.Models.responseModels;

import com.example.demo.Models.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JaksonJsonTest {

    @Test
    void transfer() throws Exception {
        Person person = new Person();
        person.setFirstname("amir");
        person.setLastname("rezvani");
        person.setNationalKey("4580396987");

        System.out.println(person.toString());

        Person jaced = (Person) JaksonJson.transfer(person.toString(), new Person());

        assertThat(jaced.getNationalKey()).isEqualTo(person.getNationalKey());
        assertThat(jaced.getLastname()).isEqualTo(person.getLastname());
        assertThat(jaced.getFirstname()).isEqualTo(person.getFirstname());
    }
}