package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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

    @ManyToOne
    private College college;



    public College getCollege() {
        return college;
    }

    public UUID getId() {
        return id;
    }

    public void setCollege(College college) {
        this.college = college;
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

    public Set<StudentLesson> getStudentLessons() {
        return studentLessons;
    }

    public void setStudentLessons(Set<StudentLesson> studentLessons) {
        this.studentLessons = studentLessons;
    }


    /**
     * method to get avg of the student
     *
     * @return overall average of the student
     */
    public double getMyAVG() {
        int unit = 0;
        double grade = 0;

        for (StudentLesson studentLesson: this.getStudentLessons()) {
            grade += studentLesson.getGrade() * studentLesson.getLesson().getUnit();
            unit += studentLesson.getLesson().getUnit();
        }

        return grade/unit;
    }
}
