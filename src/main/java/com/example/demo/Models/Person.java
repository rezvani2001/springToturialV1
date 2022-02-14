package com.example.demo.Models;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
public class Person {
    @Id
    @Column( nullable = false, unique = true, updatable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @Size(min = 2)
    private String firstname;

    @Size(min = 2)
    private String lastname;

    @Column(unique = true, nullable = false)
    private String nationalKey;

    public Person() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNationalKey() {
        return nationalKey;
    }

    public void setNationalKey(String nationalKey) {
        this.nationalKey = nationalKey;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
