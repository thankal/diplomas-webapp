package com.example.thesisapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.thesisapp.model.Application;
import com.example.thesisapp.model.Assignment;
import com.example.thesisapp.model.Student;


@Service
public interface AssignmentService {
	public void save(Assignment assignment);

	public void cancelAssignment(Long assignmentId);

	public void cancelAssignmentByThesisId(Long thesisId);

    public Optional<Student> getStudentAssigned(Long thesisId);

    public boolean assignmentExists(Long sudentId, Long thesisId);

    public boolean assignmentExists(Long thesisId);

    
}