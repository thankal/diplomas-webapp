package com.example.thesisapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thesisapp.model.Professor;

public interface ProfessorDAO extends JpaRepository<Professor, Long>{

	// Optional<Professor> findByUserId(String userId);
    List<Professor> findAll();

}
