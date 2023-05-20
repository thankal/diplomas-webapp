package com.example.thesisapp.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ApplicationObjectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.thesisapp.model.Application;
import com.example.thesisapp.model.Student;
import com.example.thesisapp.model.Thesis;

import static org.junit.Assert.*;
import org.springframework.test.context.TestPropertySource;

@SpringBootApplication
@TestPropertySource(locations="classpath:application.properties")
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
public class ApplicationDAOTest {

    private ApplicationDAO applicationDAO;

    @Before 
    public void setUp() {

    }

    @Test
    public void testApplicationDAOIsNotNull() {
        assertNotNull(applicationDAO);
    }

    @Test
    public void testDeleteAll() {
        Application application1 = new Application();
        Application application2 = new Application();

        // Empty list
        List<Application> expectedApplications = new ArrayList<>();

        applicationDAO.save(application1);
        applicationDAO.save(application2);

        applicationDAO.deleteAll();

        List<Application> actuallApplications = applicationDAO.findAll();
        assertEquals(expectedApplications, actuallApplications);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        // Create sample data
        Application application1 = new Application();
        application1.setId(id);

        // Add the sample data to the dao
        applicationDAO.save(application1);

        // Call the method to be tested
        applicationDAO.deleteById(id);

        // Assert the results
        assertFalse(applicationDAO.findById(id).isPresent());
    }

    @Test
    public void testDeleteByStudentId() {
        Long studentId = 1L;

        Application application = new Application();
        Student student = new Student();

        student.setId(studentId);
        application.setStudent(student);

        applicationDAO.save(application);
        applicationDAO.deleteByStudentId(studentId);

        // Assert the results
        assertFalse(applicationDAO.findByStudentId(studentId).size() > 0);
    }

    @Test
    public void testDeleteByStudentIdAndThesisId() {
        Long studentId = 1L;
        Long thesisId = 2L;

        Student student = new Student();
        Thesis thesis = new Thesis();

        student.setId(studentId);
        thesis.setId(thesisId);

        Application application = new Application();
        application.setStudent(student);
        application.setThesis(thesis);

        applicationDAO.save(application);
        applicationDAO.deleteByStudentIdAndThesisId(studentId, thesisId);

        // Assert the results
        assertFalse(applicationDAO.findByStudentIdAndThesisId(studentId, thesisId).isPresent());
    }

    @Test
    public void testDeleteByThesisId() {
        Long thesisId = 1L;
        Thesis thesis = new Thesis();
        thesis.setId(thesisId);

        Application application = new Application();
        application.setThesis(thesis);

        applicationDAO.save(application);
        applicationDAO.deleteByThesisId(thesisId);

        // Assert the results
        
    }

    @Test
    public void testFindAll() {
        Application application1 = new Application();
        Application application2 = new Application();

        List<Application> expectedApplications = new ArrayList<>();
        expectedApplications.add(application1); 
        expectedApplications.add(application2);

        applicationDAO.save(application1);
        applicationDAO.save(application2);

        List<Application> actuallApplications = applicationDAO.findAll();

        assertEquals(expectedApplications, actuallApplications);
    }

    @Test
    public void testFindByStudentId() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);

        Application application = new Application();
        application.setStudent(student);
        
        List<Application> expectedApplications = new ArrayList<>();
        expectedApplications.add(application);

        applicationDAO.save(application);
        
        List<Application> actuallApplications = applicationDAO.findByStudentId(studentId);
        assertEquals(expectedApplications, actuallApplications);

    }

    @Test
    public void testFindByStudentIdAndThesisId() {
        Long studentId = 1L;
        Long thesisId = 2L;

        Application application = new Application();
        Student student = new Student();
        Thesis thesis = new Thesis();

        student.setId(studentId);
        thesis.setId(thesisId);

        application.setStudent(student);
        application.setThesis(thesis);

        applicationDAO.save(application);

        Optional<Application> actuallApplication = applicationDAO.findByStudentIdAndThesisId(studentId, thesisId);
        assertEquals(application, actuallApplication.get());
    }

    @Test
    public void testFindStudentsByThesisId() {
        Long thesisId = 1L; 

        Application application1 = new Application();
        Student student1 = new Student();
        Thesis thesis1 = new Thesis();

        thesis1.setId(thesisId);

        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(student1);
        applicationDAO.save(application1);

        List<Student> actuallStudents = applicationDAO.findStudentsByThesisId(thesisId);
        assertEquals(expectedStudents, actuallStudents);
    }
}
