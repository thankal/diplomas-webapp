package com.example.thesisapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thesisapp.model.Evaluation;


public interface EvaluationDAO extends JpaRepository<Evaluation, Long>{

    public void save(Optional<Evaluation> evaluation);

    public Optional<Evaluation> findByAssignmentId(Long assignmentId);


}
