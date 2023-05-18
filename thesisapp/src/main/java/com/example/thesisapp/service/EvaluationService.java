package com.example.thesisapp.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.thesisapp.model.Evaluation;


@Service
public interface EvaluationService {
	public void save(Evaluation evaluation);

    public Optional<Evaluation> getEvaluationForThesis(Long thesisId);


    
}