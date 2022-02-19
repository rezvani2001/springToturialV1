package com.example.demo.Models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class StudentLesson {
    @Id
    @Column(nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private float grade;

    public StudentLesson(Student student, Lesson lesson) {
        this.id = UUID.randomUUID();
        this.grade = 0;
        this.student = student;
        this.lesson = lesson;
    }

    @ManyToOne
    @JoinColumn()
    private Student student;

    @ManyToOne
    @JoinColumn()
    private Lesson lesson;

    public StudentLesson() {

    }

    public Student getStudent() {
        return student;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
