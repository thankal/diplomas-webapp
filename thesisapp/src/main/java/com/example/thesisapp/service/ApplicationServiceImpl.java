package com.example.thesisapp.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thesisapp.dao.ApplicationDAO;
import com.example.thesisapp.model.Application;
import com.example.thesisapp.model.Student;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	
	@Autowired
	private ApplicationDAO applicationDao;

	@Override
	public void saveApplication(Application application) {
		applicationDao.save(application);
	}

	@Override
	public boolean applicationExists(Long sudentId, Long thesisId) {
		Optional<Application> storedApplication = applicationDao.findByStudentIdAndThesisId(sudentId, thesisId);
		return storedApplication.isPresent();
	}

	@Override
	@Transactional
	public void cancelApplication(Long applicationId) {
		applicationDao.deleteById(applicationId);
	}

	@Override
	@Transactional
	public void cancelApplicationsForThesis(Long thesisId) {
		applicationDao.deleteByThesisId(thesisId);
	}

	@Override
	@Transactional
	public void cancelApplicationsByStudent(Long studentId) {
		applicationDao.deleteByStudentId(studentId);
	}

	@Override
	public Application getApplicationByStudentIdAndThesisId(Long studentId, Long thesisId) {
		return applicationDao.findByStudentIdAndThesisId(studentId, thesisId).orElseThrow(
				()-> new RuntimeException("APPLICATION_NOT_FOUND")
			);
	}

	@Override
	public List<Long> getApplicationIdsByStudentId(Long studentId) {
		List<Application> applications = applicationDao.findByStudentId(studentId);

		List<Long> applicationIds = new ArrayList<Long>();
		for (Application application : applications) {
			applicationIds.add(application.getThesis().getId());	
		}
		
		return applicationIds;
	}

	@Override
	public List<Student> getStudentsApplied(Long thesisId) {
		return applicationDao.findStudentsByThesisId(thesisId);
	}



}
