package com.example.thesisapp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertFalse;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.thesisapp.dao.EvaluationDAO;
import com.example.thesisapp.model.Evaluation;
import com.example.thesisapp.model.Assignment;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class EvaluationServiceTest {

    @TestConfiguration
    static class EvaluationServiceImplTestContextConfiguration {
        @Bean
        public EvaluationService evaluationService() {
            return new EvaluationServiceImpl();
        }
    }

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private AssignmentService assignmentService;

    @MockBean
    private EvaluationDAO evaluationDAO;

    @Test
    public void testEvaluationDAOJpaImplIsNotNull() {
        Assertions.assertNotNull(evaluationService);
    }

    @Test
    public void testGetEvaluationForThesis_WithAssignmentExists() {
        Long thesisId = 1L;

        Assignment assignment = new Assignment();
        assignment.setId(1L);
        when(assignmentService.getAssignmentByThesisId(thesisId)).thenReturn(Optional.of(assignment));

        Evaluation evaluation = new Evaluation();
        evaluation.setId(1L);
        when(evaluationDAO.findByAssignmentId(assignment.getId())).thenReturn(Optional.of(evaluation));

        Optional<Evaluation> result = evaluationService.getEvaluationForThesis(thesisId);

        assertEquals(Optional.of(evaluation), result);
        Mockito.verify(assignmentService).getAssignmentByThesisId(thesisId);
        Mockito.verify(evaluationDAO).findByAssignmentId(assignment.getId());
    }

    @Test 
    public void testGetEvaluationForThesis_WithAssignmentNotExists(){
        Long thesisId = 1L;

        when(assignmentService.getAssignmentByThesisId(thesisId)).thenReturn(Optional.empty());

        Optional<Evaluation> result = evaluationService.getEvaluationForThesis(thesisId);
        Mockito.verify(assignmentService).getAssignmentByThesisId(thesisId);
        Mockito.verifyNoInteractions(evaluationDAO);
    }

    @Test
    public void testSave() {
        Evaluation evaluation = new Evaluation();
        Mockito.doNothing().when(evaluationDAO).save(evaluation);
        evaluationService.save(evaluation);
        Mockito.verify(evaluationDAO).save(evaluation);
    }
}
