package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Student {
    @Id
    @Column( nullable = false, unique = true, updatable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @OneToOne
    @JsonUnwrapped
    @JoinColumn(nullable = false, updatable = false)
    private Person person;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private Set<StudentLesson> studentLessons;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
