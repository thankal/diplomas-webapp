package com.example.thesisapp.model;

import java.util.List;

public class FewestCoursesSelectionStrategy implements SelectionStrategy {

    @Override
    public Student selectCandidate(List<Student> candidates) {
        Student bestStudent = candidates.get(0);

        for (Student student : candidates ) {
            if (bestStudent.getRemainingCourses() > student.getRemainingCourses()) {
                bestStudent = student;
            }
        }

        return bestStudent;
    }

}