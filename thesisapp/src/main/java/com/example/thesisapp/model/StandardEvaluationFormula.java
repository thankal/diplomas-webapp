package com.example.thesisapp.model;

public class StandardEvaluationFormula implements EvaluationFormula{

    @Override
    public double calculateTotalGrade(double implementation, double report, double presentation) {
        return (0.7*implementation + 0.15*report + 0.15*presentation);
    }
    
}
