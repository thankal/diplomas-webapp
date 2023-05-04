package com.example.thesisapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thesisapp.dao.StudentDAO;
import com.example.thesisapp.model.Student;

@Service
public class StudentServiceImpl implements StudentService {

	
	@Autowired
	private StudentDAO studentDAO;
	
	@Override
	public void saveStudent(Student student) {
        studentDAO.save(student);	
    }

}
