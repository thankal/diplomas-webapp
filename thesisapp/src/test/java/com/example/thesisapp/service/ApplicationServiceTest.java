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

import com.example.thesisapp.dao.ApplicationDAO;
import com.example.thesisapp.model.Application;
import com.example.thesisapp.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class ApplicationServiceTest {

    @TestConfiguration
    static class ApplicationServiceImplTestContextConfiguration {
        @Bean
        public ApplicationService applicationService() {
            return new ApplicationServiceImpl();
        }
    }

    @Autowired
    private ApplicationService applicationService;

    @MockBean
    private ApplicationDAO applicationDAO;


    @Test
    public void testApplicationDAOJpaImplIsNotNull() {
        Assertions.assertNotNull(applicationService);
    }

    @Test
    public void testApplicationExists() {
        Mockito.when(applicationDAO.findByStudentIdAndThesisId(1L, 1L)).thenReturn(Optional.of(new Application()));
        boolean exists = applicationService.applicationExists(1L, 1L);
        Assertions.assertTrue(exists);
    }

    @Test
    public void testCancelApplicationWithGivenApplicationId() {
        Mockito.doNothing().when(applicationDAO).deleteById(1L);
        applicationService.cancelApplication(1L);
        Mockito.verify(applicationDAO).deleteById(1L);
    }

    @Test
    public void testCancelAllApplicationsWithGivenThesisId() {
        Mockito.doNothing().when(applicationDAO).deleteByThesisId(1L);
        applicationService.cancelApplicationsForThesis(1L);
        Mockito.verify(applicationDAO).deleteByThesisId(1L);
    }

    @Test
    public void testCancelAllApplicationsWithGivenStudentId() {
        Mockito.doNothing().when(applicationDAO).deleteByStudentId(1L);
        applicationService.cancelApplicationsByStudent(1L);
        Mockito.verify(applicationDAO).deleteByStudentId(1L);
    }

    @Test
    public void testFetchExistingApplicationWithThesisAndStudentIds() {
        Application mockApplication = new Application();
        Mockito.when(applicationDAO.findByStudentIdAndThesisId(1L, 1L)).thenReturn(Optional.of(mockApplication)); 

        Application application = applicationService.getApplicationByStudentIdAndThesisId(1L, 1L);
        Assertions.assertSame(application, mockApplication);
        Mockito.verify(applicationDAO).findByStudentIdAndThesisId(1L, 1L);
    }

    @Test
    public void testFetchNonExistentApplicationWithThesisAndStudentIds() {
        Mockito.when(applicationDAO.findByStudentIdAndThesisId(1L, 2L)).thenReturn(Optional.empty()); 

        Assertions.assertThrows(RuntimeException.class, () ->
                applicationService.getApplicationByStudentIdAndThesisId(1L, 2L)
        );

        Mockito.verify(applicationDAO).findByStudentIdAndThesisId(1L, 2L);
    }

    @Test
    public void testGetAListOfStudentThatApplied() {
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(new Student());
        mockStudents.add(new Student());
        Mockito.when(applicationDAO.findStudentsByThesisId(1L)).thenReturn(mockStudents); 

        List<Student> students = applicationService.getStudentsApplied(1L);

        Assertions.assertSame(students, mockStudents);
        Assertions.assertEquals(2, students.size());


        Mockito.verify(applicationDAO).findStudentsByThesisId(1L);
    }

}

