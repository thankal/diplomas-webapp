package com.example.thesisapp.service;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.mockito.InjectMocks;

import com.example.thesisapp.service.AssignmentServiceImpl;
import com.example.thesisapp.dao.AssignmentDAO;
import com.example.thesisapp.model.Assignment;
import com.example.thesisapp.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class AssignmentServiceTest {

    @TestConfiguration
    static class AssignmentServiceImplTestContextConfiguration {
        @Bean
        public AssignmentService assignmentService() {
            return new AssignmentServiceImpl();
        }
    }

    @Autowired
    private AssignmentService assignmentService;

    @MockBean
    private AssignmentDAO assignmentDAO;

    @Test 
    public void testAssignmentDAOJpaImplIsNotNull() {
        Assertions.assertNotNull(assignmentService);
    }

    @Test
    public void testAssignmentExists() {
        Mockito.when(assignmentDAO.findByThesisIdAndStudentId(1L, 1L)).thenReturn(Optional.of(new Assignment()));
        boolean exists = assignmentService.assignmentExists(1L, 1L);
        Assertions.assertTrue(exists);
    }

    @Test
    public void testAssignmentExists2() {
        Mockito.when(assignmentDAO.findByThesisId(1L)).thenReturn(Optional.of(new Assignment()));
        boolean exists = assignmentService.assignmentExists(1L);
        Assertions.assertTrue(exists);
    }

    @Test
    public void testCancelAssignmentByThesisId() {
        Mockito.doNothing().when(assignmentDAO).deleteById(1L);
        assignmentService.cancelAssignmentByThesisId(1L);
        Mockito.verify(assignmentDAO).deleteById(1L);
    }

    @Test
    public void testGetAssignmentByThesisId() {
        Mockito.when(assignmentDAO.findByThesisId(1L)).thenReturn(Optional.of(new Assignment()));
        Optional<Assignment> assignment = assignmentService.getAssignmentByThesisId(1L);
        Assertions.assertTrue(assignment.isPresent());
    }

    @Test
    public void testGetStudentAssigned() {
        Mockito.when(assignmentDAO.findByThesisId(1L)).thenReturn(Optional.of(new Assignment()));
        Optional<Student> student = assignmentService.getStudentAssigned(1L);
        Assertions.assertTrue(student.isPresent());
    }

    @Test
    public void testGetThesisIdByStudent() {
        Mockito.when(assignmentDAO.findAllUniqueThesisIdByStudent(1L)).thenReturn(Optional.of(1L));
        Optional<Long> thesisId = assignmentService.getThesisIdByStudent(1L);
        Assertions.assertTrue(thesisId.isPresent());
    }

    @Test
    public void testGetThesisIds() {
        Mockito.when(assignmentDAO.findAllUniqueThesisIds()).thenReturn(new ArrayList<Long>());
        List<Long> thesisIds = assignmentService.getThesisIds();
        Assertions.assertNotNull(thesisIds);
    }

    @Test
    public void testSave() {
        Assignment assignment = new Assignment();
        Mockito.when(assignmentDAO.save(assignment)).thenReturn(assignment);
        assignmentService.save(assignment);
        Mockito.verify(assignmentDAO).save(assignment);

    }
}
