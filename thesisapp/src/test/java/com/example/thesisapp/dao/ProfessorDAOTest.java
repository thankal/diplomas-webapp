package com.example.thesisapp.dao;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.thesisapp.model.Professor;
import com.example.thesisapp.model.User;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class ProfessorDAOTest {

    @Autowired
    private ProfessorDAO professorDAO;

    Professor professor = new Professor();

    @Before
    public void setUp() {
        //
    }

    @Test 
    public void testProfessorIsNotNull() {
        Assertions.assertNotNull(professorDAO);
    }

    @Test
    public void testFindAll() {
        // Create sample data
        Professor professor1 = new Professor();
        Professor professor2 = new Professor();

        // Create expected result
        List<Professor> expectedProfessors = new ArrayList<>();

        // Add sample data to expected result
        expectedProfessors.add(professor1);
        expectedProfessors.add(professor2);

        // Create actual result
        List<Professor> actualProfessors = professorDAO.findAll();

        // Assert the results
        assertEquals(expectedProfessors, actualProfessors);

    }

    @Test
    public void testFindById() {
        Long professorId = 1L;

        // Create sample data
        Professor professor1 = new Professor();
        professor1.setId(professorId);
        
        // Add sample data to the dao
        professorDAO.save(professor1);

        // Call the method to be tested
        Optional<Professor> actualProfessor = professorDAO.findById(professorId);

        // Assert the results
        assertTrue(actualProfessor.isPresent());
        assertEquals(professor1, actualProfessor);

    }

    @Test
    public void testFindByUser() {
        User user = new User();

        // Create sample data
        Professor professor1 = new Professor();
        professor1.setUser(user);

        // Add sample data to the dao
        professorDAO.save(professor1);

        // Call the method to be tested
        Optional<Professor> actualProfessor = professorDAO.findByUser(user);

        // Assert the results
        assertTrue(actualProfessor.isPresent());
        assertEquals(professor1, actualProfessor);

    }
}
