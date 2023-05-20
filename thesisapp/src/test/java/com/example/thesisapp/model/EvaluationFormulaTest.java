package com.example.thesisapp.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.math.*;

public class EvaluationFormulaTest {

    EvaluationFormula evaluationFormula = new StandardEvaluationFormula();

    @Test
    public void testCalculateTotalGrade() {
        double implementation = 80.0;
        double report = 75.0;
        double presentation = 90.0;

        double expectedGrade = implementation*0.7 + report*0.15 + presentation*0.15;
        BigDecimal expectedGradeRounded = BigDecimal.valueOf(expectedGrade).setScale(2, RoundingMode.HALF_UP);
        double expectedFinalGrade = expectedGradeRounded.doubleValue();

        double finalGrade = evaluationFormula.calculateTotalGrade(implementation, report, presentation);

        assertEquals(expectedFinalGrade, finalGrade, 0.001);
        
    }
}
