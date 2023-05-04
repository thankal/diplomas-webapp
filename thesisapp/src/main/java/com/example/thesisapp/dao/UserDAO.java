package com.example.thesisapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thesisapp.model.User;

public interface UserDAO extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

	// Optional<User> findByEmail(String email);

}

