package com.example.thesisapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.thesisapp.model.Thesis;

@Repository
public interface ThesisDAO extends JpaRepository<Thesis, Long> {
	
	public Optional<Thesis> findById(int theId);
		
}