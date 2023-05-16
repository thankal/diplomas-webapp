package com.example.thesisapp.model;

import java.util.List;

public interface SelectionStrategy {
    public Student selectCandidate(List<Student> candidates);
    
}
