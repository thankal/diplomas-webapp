package com.example.thesisapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thesisapp.dao.StudentDAO;
import com.example.thesisapp.model.Student;
import com.example.thesisapp.model.User;

@Service
public class StudentServiceImpl implements StudentService {

	
	@Autowired
	private StudentDAO studentDAO;
	
	@Override
	public void saveStudent(Student student) {
        studentDAO.save(student);	
    }

	@Override
	public Student findStudentByUser(User user) {
		return studentDAO.findByUser(user).orElseThrow(
				()-> new RuntimeException("STUDENT_NOT_FOUND")
			);
	}

	@Override
	public Student findById(Long id) {
		return studentDAO.findById(id).orElseThrow(
				()-> new RuntimeException("STUDENT_NOT_FOUND")
			);
	}

}
