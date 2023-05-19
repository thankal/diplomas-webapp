package com.example.thesisapp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StandardEvaluationFormula implements EvaluationFormula{

    @Override
    public double calculateTotalGrade(double implementation, double report, double presentation) {
        return BigDecimal.valueOf(0.7*implementation + 0.15*report + 0.15*presentation).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    
}
