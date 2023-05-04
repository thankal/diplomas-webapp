package com.example.thesisapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thesisapp.dao.ProfessorDAO;
import com.example.thesisapp.model.Professor;

@Service
public class ProfessorServiceImpl implements ProfessorService {

	
	@Autowired
	private ProfessorDAO professorDAO;
	
	@Override
	public void saveProfessor(Professor professor) {
        professorDAO.save(professor);	
    }

}
