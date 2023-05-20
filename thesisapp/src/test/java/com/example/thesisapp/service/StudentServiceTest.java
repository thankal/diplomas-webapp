package com.example.thesisapp.service;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.thesisapp.dao.StudentDAO;
import com.example.thesisapp.model.Student;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class StudentServiceTest {

    @TestConfiguration
    static class StudentServiceImplTestContextConfiguration {
        @Bean
        public StudentService studentService() {
            return new StudentServiceImpl();
        }
    }

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentDAO studentDAO;

    @Test
    public void testStudentDAOJpaImplIsNotNull() {
        Assertions.assertNotNull(studentService);
    }

    @Test
    public void testFindById() {
        Student student = new Student();
        Mockito.when(studentDAO.findById(1L)).thenReturn(Optional.of(student));
        Student foundStudent = studentService.findById(1L);
        Assertions.assertEquals(student, foundStudent);
    }

    @Test
    public void testFindStudentByUser() {
        Student student = new Student();
        Mockito.when(studentDAO.findByUser(Mockito.any())).thenReturn(Optional.of(student));
        Student foundStudent = studentService.findStudentByUser(new Student().getUser());
        Assertions.assertEquals(student, foundStudent);
    }

    @Test
    public void testSaveStudent() {
        Student student = new Student();
        Mockito.when(studentDAO.save(student)).thenReturn(student);
        studentService.saveStudent(student);
        Mockito.verify(studentDAO).save(student);
    }
}
