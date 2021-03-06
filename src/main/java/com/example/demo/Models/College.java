package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Entity
public class College {
    @Id
    @Column(unique = true, updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @NotNull(message = "college name is required")
    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 255)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Teacher> teachers;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Student> students;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Lesson> lessons;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
}
