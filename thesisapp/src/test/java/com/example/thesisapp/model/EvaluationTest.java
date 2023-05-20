package com.example.thesisapp.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class EvaluationTest {
    private Evaluation evaluation ;

    @Before
    public void setUp(){
         evaluation = new Evaluation();
    }    
    @Test
    public void testSetGetAssignment() {
        Assignment assignment = new Assignment();
        evaluation.setAssignment(assignment);
        Assert.assertEquals(assignment, evaluation.getAssignment());
    }

    @Test
    public void testSetGetId() {
        Long id = 1L;
        evaluation.setId(id);
        Assert.assertEquals(id, evaluation.getId());

    }

    @Test
    public void testSetGetImplementation() {
        Double implementation = 1.0;
        evaluation.setImplementation(implementation);
        Assert.assertEquals(implementation, evaluation.getAssignment());
    }


    @Test
    public void testSetGetPresentation() {
        Double presentation = 1.0;
        evaluation.setPresentation(presentation);
        Assertions.assertEquals(presentation, evaluation.getPresentation());

    }

    @Test
    public void testSetGetReport() {
        Double report = 1.0;
        evaluation.setReport(report);
        Assertions.assertEquals(report, evaluation.getReport());

    }

    @Test
    public void testSetGetTotal() {
        Double total = 1.0;
        evaluation.setTotal(total);
        Assertions.assertEquals(total, evaluation.getTotal());

    }


}
