package com.example.thesisapp.service;

import org.springframework.stereotype.Service;

import com.example.thesisapp.model.Professor;
import com.example.thesisapp.model.User;

@Service
public interface ProfessorService {
	public void saveProfessor(Professor professor);

    public Professor findProfessorByUser(User user);

	public Professor findById(Long id);
}