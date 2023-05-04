package com.example.thesisapp.service;

import org.springframework.stereotype.Service;

import com.example.thesisapp.model.Professor;

@Service
public interface ProfessorService {
	public void saveProfessor(Professor professor);
}