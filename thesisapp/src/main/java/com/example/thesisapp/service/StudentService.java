package com.example.thesisapp.service;

import org.springframework.stereotype.Service;

import com.example.thesisapp.model.Student;

@Service
public interface StudentService {
	public void saveStudent(Student student);
}