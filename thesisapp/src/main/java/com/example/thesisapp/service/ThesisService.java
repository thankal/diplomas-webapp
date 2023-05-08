package com.example.thesisapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.thesisapp.model.Thesis;

@Service
public interface ThesisService {
	public List<Thesis> findAll();
	
	public Thesis findById(long theId);
	
	public void save(Thesis theThesis);
	
	public void deleteById(long theId);
    
}
