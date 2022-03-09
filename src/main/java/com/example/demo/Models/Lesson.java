package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;
import java.util.UUID;

@Entity
public class Lesson {
    @Id
    @Column(updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Min(value = 1)
    @NonNull
    private int unit;

    @NonNull
    private String name;

    @ManyToOne
    private College college;

    @OneToMany
    @JsonIgnore
    private Set<Teacher> teachers;

    @OneToMany(mappedBy = "lesson")
    @JsonIgnore
    private Set<StudentLesson> students;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Set<StudentLesson> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentLesson> students) {
        this.students = students;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}
