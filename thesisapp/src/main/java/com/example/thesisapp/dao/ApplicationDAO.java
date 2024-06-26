package com.example.thesisapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.thesisapp.model.Application;
import com.example.thesisapp.model.Student;

public interface ApplicationDAO extends JpaRepository<Application, Long>{

    List<Application> findAll();

	Optional<Application> findByStudentIdAndThesisId(Long sudentId, Long thesisId);

    void deleteByStudentIdAndThesisId(Long studentId, Long thesisId);

    void deleteByStudentId(Long studentId);

    void deleteByThesisId(Long thesisId);

    void deleteById(Long applicationId);

    void deleteAll();

    List<Application> findByStudentId(Long studentId);

    @Query("SELECT app.student FROM Application app WHERE app.thesis.id = :thesisId")
    List<Student> findStudentsByThesisId(@Param("thesisId") Long thesisId);

}
