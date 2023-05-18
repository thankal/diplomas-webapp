package com.example.thesisapp.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thesisapp.dao.EvaluationDAO;
import com.example.thesisapp.model.Assignment;
import com.example.thesisapp.model.Evaluation;

@Service
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	private EvaluationDAO evaluationDao;

	@Autowired
	private AssignmentService assignmentService;


	@Override
	public void save(Evaluation evaluation) {
		evaluationDao.save(evaluation);
	}


	@Override
	public Optional<Evaluation> getEvaluationForThesis(Long thesisId) {
		Assignment assignment = assignmentService.getAssignmentByThesisId(thesisId).orElse(null);
		if (assignment == null) {
			return Optional.empty();
		}
		return evaluationDao.findByAssignmentId(assignment.getId());
		
	}


}
