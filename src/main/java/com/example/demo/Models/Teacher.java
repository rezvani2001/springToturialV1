package com.example.demo.Models;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Teacher {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @OneToOne
    @JsonUnwrapped
    @JoinColumn(nullable = false, updatable = false)
    private Person person;

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
