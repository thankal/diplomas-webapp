package com.example.thesisapp.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.example.thesisapp.model.Assignment;
import com.example.thesisapp.model.Evaluation;

import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class EvaluationDAOTest {
    
    private EvaluationDAO evaluationDAO;

    Evaluation evaluation = new Evaluation();

    Assignment assignment = new Assignment();

    @Before
    public void setUp() {
        
    }


    @Test
    void testEvaluationDAOIsNotNull() {
        Assertions.assertNotNull(evaluationDAO);
    }

    @Test
    public void testFindByAssignmentId() {
        Long assignmentId = 1L;
        Assignment assignment1 = new Assignment();
        assignment1.setId(assignmentId);

        evaluation.setAssignment(assignment1);

        evaluationDAO.save(evaluation);
        evaluationDAO.findByAssignmentId(assignmentId);
    }

    @Test
    public void testSave() {
        evaluationDAO.save(evaluation);
        Assertions.assertNotNull(evaluationDAO);
    }
}
