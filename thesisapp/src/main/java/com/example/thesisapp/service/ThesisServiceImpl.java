package com.example.thesisapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thesisapp.dao.ThesisDAO;
import com.example.thesisapp.model.Thesis;

@Service
public class ThesisServiceImpl implements ThesisService{

	@Autowired
	private ThesisDAO thesisRepository;
	
	@Autowired
	public ThesisServiceImpl(ThesisDAO theEmployeeRepository) {
		thesisRepository = theEmployeeRepository;
	}
	
	public ThesisServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional
	public List<Thesis> findAll() {
		return thesisRepository.findAll();
	}

	@Override
	@Transactional
	public Thesis findById(long theId) {
		Thesis result = thesisRepository.findById(theId).orElseThrow(
                ()-> new RuntimeException("THESIS_NOT_FOUND")
            );
				
		if (result != null ) {
			return result;
		}
		else {
			// we didn't find the thesis
			throw new RuntimeException("Did not find thesis id - " + theId);
		}
	}

	@Override
	@Transactional
	public void save(Thesis theEmployee) {
		thesisRepository.save(theEmployee);
	}

	@Override
	@Transactional
	public void deleteById(long theId) {
		thesisRepository.deleteById(theId);
	}
}
