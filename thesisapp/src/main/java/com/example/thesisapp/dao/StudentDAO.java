package com.example.thesisapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thesisapp.model.Student;

public interface StudentDAO extends JpaRepository<Student, Long> {
	
    List<Student> findAll();
	// Optional<Student> findByUserId(String userId);

}

