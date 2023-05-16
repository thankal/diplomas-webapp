package com.example.thesisapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thesisapp.model.Assignment;
import com.example.thesisapp.model.Student;


public interface AssignmentDAO extends JpaRepository<Assignment, Long>{

	Optional<Assignment> findByThesisIdAndStudentId(Long thesisId, Long studentId);

    void deleteByStudentIdAndThesisId(Long studentId, Long thesisId);

    void deleteById(Long assignmentId);

    void deleteByThesisId(Long thesisId);

    Optional<Assignment> findByStudentId(Long studentId);

    Optional<Assignment> findByThesisId(Long studentId);



}
