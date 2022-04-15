package com.example.demo.services;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.Person;
import com.example.demo.Models.Student;
import com.example.demo.Repositories.PersonRepository;
import com.example.demo.Repositories.StudentRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private PersonService personService;

    @Before
    public void setUp() throws GeneralException {
        Person person = new Person();
        person.setNationalKey("4580396987");
        person.setFirstname("amir");
        person.setLastname("rezvani");

        studentService.insertStudent(person);
    }

    @Test
    public void getAllStudents() {
        List<Student> students = studentService.getAllStudents();

        assertThat(students.size()).isEqualTo(1);
    }
}