package com.example.thesisapp.service;

import org.springframework.stereotype.Service;

import com.example.thesisapp.model.Student;
import com.example.thesisapp.model.User;

@Service
public interface StudentService {
	public void saveStudent(Student student);

	public Student findStudentByUser(User user);

	public Student findById(Long id);
}