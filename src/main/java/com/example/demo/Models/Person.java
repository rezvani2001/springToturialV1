package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
public class Person {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    @JsonIgnore
    private UUID id;

    @NotNull(message = "firstname is required")
    @Size(min = 2, message = "firstname must have 2 characters at least")
    private String firstname;

    @NotNull(message = "lastname is required")
    @Size(min = 2, message = "lastname must have 2 characters at least")
    private String lastname;

    @Column(unique = true, nullable = false)
    @NotNull(message = "national code is required")
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
