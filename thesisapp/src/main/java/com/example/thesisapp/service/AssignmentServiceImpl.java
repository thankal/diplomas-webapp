package com.example.thesisapp.service;


import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thesisapp.dao.AssignmentDAO;
import com.example.thesisapp.model.Assignment;
import com.example.thesisapp.model.Student;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	private AssignmentDAO assignmentDao;


	@Override
	public void save(Assignment assignment) {
		assignmentDao.save(assignment);
	}

	@Override
	@Transactional
	public void cancelAssignment(Long assignmentId) {
		assignmentDao.deleteById(assignmentId);
	}

	@Override
	@Transactional
	public void cancelAssignmentByThesisId(Long thesisId) {
		assignmentDao.deleteByThesisId(thesisId);
	}

	@Override
	public Optional<Student> getStudentAssigned(Long thesisId) {
		Optional<Assignment> assignment = assignmentDao.findByThesisId(thesisId);
		if (assignment.isPresent()) {
			return Optional.of(assignment.get().getStudent());
		}

		return Optional.empty();

	}

	@Override
	public boolean assignmentExists(Long studentId, Long thesisId) {
		Optional<Assignment> storedAssignment = assignmentDao.findByThesisIdAndStudentId(thesisId, studentId);
		return storedAssignment.isPresent();
	}

	@Override
	public boolean assignmentExists(Long thesisId) {
		Optional<Assignment> storedAssignment = assignmentDao.findByThesisId(thesisId);
		return storedAssignment.isPresent();
	}

	@Override
	public List<Long> getThesisIds() {
		return assignmentDao.findAllUniqueThesisIds();
	}

	@Override
	public Optional<Long> getThesisIdByStudent(Long studentId) {
		return assignmentDao.findAllUniqueThesisIdByStudent(studentId);
	}

	@Override
	public Optional<Assignment> getAssignmentByThesisId(Long thesisId) {
		return assignmentDao.findByThesisId(thesisId);
	}
}
