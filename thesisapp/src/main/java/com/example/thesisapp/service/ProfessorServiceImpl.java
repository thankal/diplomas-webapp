package com.example.thesisapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thesisapp.dao.ProfessorDAO;
import com.example.thesisapp.model.Professor;
import com.example.thesisapp.model.User;

@Service
public class ProfessorServiceImpl implements ProfessorService {

	
	@Autowired
	private ProfessorDAO professorDAO;
	
	@Override
	public void saveProfessor(Professor professor) {
        professorDAO.save(professor);	
    }

	@Override
	public Professor findProfessorByUser(User user) {
		return professorDAO.findByUser(user).orElseThrow(
				()-> new RuntimeException("PROFESSOR_NOT_FOUND")
			);
	}

	@Override
	public Professor findById(Long id) {
		return professorDAO.findById(id).orElseThrow(
				()-> new RuntimeException("PROFESSOR_NOT_FOUND")
			);
	}

}
