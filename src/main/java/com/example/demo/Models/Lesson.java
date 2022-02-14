package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;
import java.util.UUID;

@Entity
public class Lesson {
    @Id
    @Column(updatable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @Min(value = 1)
    private int unit;

    @OneToMany
    @JsonIgnore
    private Set<Teacher> teachers;

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
}
