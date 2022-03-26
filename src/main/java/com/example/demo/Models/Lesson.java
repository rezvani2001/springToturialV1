package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity
public class Lesson {
    @Id
    @Column(updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Min(value = 1, message = "unit value must be at least 1")
    @NotNull(message = "lesson's unit is required")
    private int unit;

    @NotNull(message = "lesson's name is required")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private College college;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Teacher> teachers;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
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
