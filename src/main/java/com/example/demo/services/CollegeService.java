package com.example.demo.services;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.College;
import com.example.demo.Models.Lesson;
import com.example.demo.Models.Student;
import com.example.demo.Models.Teacher;
import com.example.demo.Models.messages.CollegeMessages;
import com.example.demo.Repositories.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CollegeService {
    private final CollegeRepository collegeRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final LessonService lessonService;


    @Autowired
    public CollegeService(CollegeRepository repository, StudentService studentService,
                          TeacherService teacherService, LessonService lessonService) {
        this.collegeRepository = repository;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.lessonService = lessonService;
    }


    public void insertCollege(College college) throws GeneralException {
        if (collegeRepository.findCollegeByName(college.getName()).isPresent()) {
            throw new GeneralException(CollegeMessages.DUPLICATED_NAME);
        } else {
            college.setId(UUID.randomUUID());
            collegeRepository.save(college);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    public void assignStudent(UUID studentId, UUID collegeId) throws GeneralException {
        Student student = studentService.getStudentById(studentId);
        College college = this.getCollegeById(collegeId);

        this.grantStudent(student, college);
        studentService.grantCollege(student, college);
    }

    public void assignTeacher(UUID teacherId, UUID collegeId) throws GeneralException {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        College college = this.getCollegeById(collegeId);

        this.grantTeacher(teacher, college);
        teacherService.grantCollege(teacher, college);
    }

    public void assignLesson(UUID lessonId, UUID collegeId) throws GeneralException {
        Lesson lesson = lessonService.getLessonById(lessonId);
        College college = this.getCollegeById(collegeId);

        this.grantLesson(lesson, college);
        lessonService.grantCollege(lesson, college);
    }
    ////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////
    public void grantTeacher(Teacher teacher, College college) {
        college.getTeachers().add(teacher);
        collegeRepository.save(college);
    }

    private void grantStudent(Student student, College college) {
        college.getStudents().add(student);
        collegeRepository.save(college);
    }

    private void grantLesson(Lesson lesson, College college) {
        college.getLessons().add(lesson);
        collegeRepository.save(college);
    }
    ////////////////////////////////////////////////////////////////////////////

    public College getCollegeById(UUID collegeId) throws GeneralException {
        Optional<College> college = collegeRepository.findById(collegeId);

        if (college.isPresent()) {
            return college.get();
        } else {
            throw new GeneralException(CollegeMessages.NOT_FOUND);
        }
    }

    public List<College> getAllColleges() {
        return (List<College>) collegeRepository.findAll();
    }
}
