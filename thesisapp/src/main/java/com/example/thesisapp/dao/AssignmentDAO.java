package com.example.thesisapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.thesisapp.model.Assignment;


public interface AssignmentDAO extends JpaRepository<Assignment, Long>{

	Optional<Assignment> findByThesisIdAndStudentId(Long thesisId, Long studentId);

    void deleteByStudentIdAndThesisId(Long studentId, Long thesisId);

    void deleteByThesisId(Long thesisId);

    Optional<Assignment> findByStudentId(Long studentId);

    Optional<Assignment> findByThesisId(Long studentId);

    @Query("SELECT DISTINCT app.thesis.id FROM Assignment app")
    List<Long> findAllUniqueThesisIds();

    @Query("SELECT DISTINCT app.thesis.id FROM Assignment app WHERE app.student.id = :studentId")
    Optional<Long> findAllUniqueThesisIdByStudent(Long studentId);




}
