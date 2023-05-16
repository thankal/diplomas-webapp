package com.example.thesisapp.model;

import java.util.List;
import java.util.Random;

public class RandomSelectionStrategy implements SelectionStrategy {
    @Override
    public Student selectCandidate(List<Student> candidates) {
        // randomly select a candidate from the list
        Random rand = new Random();
        Student randomStudent = candidates.get(rand.nextInt(candidates.size()));
        return randomStudent;
    }

}

