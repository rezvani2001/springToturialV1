package com.example.demo.services;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.College;
import com.example.demo.Models.Person;
import com.example.demo.Models.Student;
import com.example.demo.Models.StudentLesson;
import com.example.demo.Models.messages.StudentMessages;
import com.example.demo.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final PersonService personService;

    @Autowired
    public StudentService(StudentRepository studentRepository, PersonService personService) {
        this.studentRepository = studentRepository;
        this.personService = personService;
    }

    ////////////////////////////////////////////////////////////////////////////

    public void insertStudent(Person person) throws GeneralException {
        personService.insertPerson(person);

        Student student = new Student();
        student.setId(UUID.randomUUID());
        student.setPerson(person);

        studentRepository.save(student);
    }

    public double getStudentAVG(UUID studentId) throws GeneralException {
        return this.getStudentById(studentId).getMyAVG();
    }

    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////

    public void grantCollege(Student student, College college) {
        student.setCollege(college);
        studentRepository.save(student);
    }

    public void grantLesson(StudentLesson studentLesson) {
        studentLesson.getStudent().getStudentLessons().add(studentLesson);
        studentRepository.save(studentLesson.getStudent());
    }

    ////////////////////////////////////////////////////////////////////////////


    public Student getStudentById(UUID studentId) throws GeneralException {
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isPresent()) {
            return student.get();
        } else throw new GeneralException(StudentMessages.NOT_FOUND);
    }

    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }
}
