package com.example.thesisapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.thesisapp.model.Application;


@Service
public interface ApplicationService {
	public void saveApplication(Application application);

	public void cancelApplication(Long applicationId);

    // TODO; maybe not needed
    public boolean applicationExists(Long sudentId, Long thesisId);

    public List<Long> getApplicationIdsByStudentId(Long studentId);

    public Application getApplicationByStudentIdAndThesisId(Long studentId, Long thesisId);
    
    // public Professor findProfessorByUser(User user);
}