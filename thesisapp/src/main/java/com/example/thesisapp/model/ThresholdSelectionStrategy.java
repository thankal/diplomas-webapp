package com.example.thesisapp.model;

import java.util.ArrayList;
import java.util.List;

public class ThresholdSelectionStrategy implements SelectionStrategy {
    private double averageGradeThreshold;
    private int remainingCoursesThreshold;

    public ThresholdSelectionStrategy (double th1, int th2) {
        averageGradeThreshold = th1;
        remainingCoursesThreshold = th2;
    }

    @Override
    public Student selectCandidate(List<Student> candidates) {
        List<Student> qualifiedCandidates = new ArrayList<Student>();

        // filter all the qualified students from the list
        for (Student student : candidates) {
            if (student.getAverageGrade() > averageGradeThreshold
                && student.getRemainingCourses() < remainingCoursesThreshold) {
                    qualifiedCandidates.add(student);
                }
        }

        // from the qualified students select the one with the biggest average grade
        Student bestStudent = qualifiedCandidates.get(0);

        for (Student student : qualifiedCandidates ) {
            if (bestStudent.getAverageGrade() < student.getAverageGrade()) {
                bestStudent = student;
            }
        }

        return bestStudent;


        
    }
    
}
