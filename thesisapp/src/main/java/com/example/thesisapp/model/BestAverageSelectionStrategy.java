package com.example.thesisapp.model;

import java.util.List;

public class BestAverageSelectionStrategy implements SelectionStrategy{

    @Override
    public Student selectCandidate(List<Student> candidates) {
        Student bestStudent = candidates.get(0);

        for (Student student : candidates ) {
            if (bestStudent.getAverageGrade() < student.getAverageGrade()) {
                bestStudent = student;
            }
        }

        return bestStudent;
    }
    
}
