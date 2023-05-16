package com.example.thesisapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.thesisapp.model.Application;
import com.example.thesisapp.model.Student;


@Service
public interface ApplicationService {
	public void saveApplication(Application application);

	public void cancelApplication(Long applicationId);

	public void cancelAllAplications();

    public boolean applicationExists(Long sudentId, Long thesisId);

    public List<Long> getApplicationIdsByStudentId(Long studentId);

    public Application getApplicationByStudentIdAndThesisId(Long studentId, Long thesisId);

    public List<Student> getStudentsApplied(Long thesisId);
    
    // public Professor findProfessorByUser(User user);
}