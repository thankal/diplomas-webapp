package com.example.thesisapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thesisapp.model.Professor;
import com.example.thesisapp.model.User;

public interface ProfessorDAO extends JpaRepository<Professor, Long>{

	// Optional<Professor> findByUserId(String userId);
    List<Professor> findAll();

    Optional<Professor> findByUser(User user);

}
