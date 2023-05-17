package com.example.thesisapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thesisapp.model.Student;
import com.example.thesisapp.model.User;

public interface StudentDAO extends JpaRepository<Student, Long> {
	
    List<Student> findAll();

    Optional<Student> findByUser(User user);

    Optional<Student> findById(Long id);

}

