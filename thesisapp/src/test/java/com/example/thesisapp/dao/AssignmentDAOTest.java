package com.example.thesisapp.dao;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.thesisapp.model.Assignment;
import com.example.thesisapp.model.Student;
import com.example.thesisapp.model.Thesis;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class AssignmentDAOTest {

    @Autowired
    private AssignmentDAO assignmentDAO;

    Assignment assignment = new Assignment();

    @Test
    public void testDeleteById() {
        Long assignmentId = 1L;
        assignment.setId(assignmentId);

        assignmentDAO.save(assignment);
        assignmentDAO.deleteById(assignmentId);

        assertFalse(assignmentDAO.findById(assignmentId).isPresent());
    }

    @Test
    public void testDeleteByStudentIdAndThesisId() {
        Long studentId = 1L;
        Long thesisId = 2L;
        Student student = new Student();
        Thesis thesis = new Thesis();

        student.setId(studentId);
        assignment.setId(thesisId);

        assignment.setStudent(student);
        assignment.setThesis(thesis);

        assignmentDAO.save(assignment);

        assignmentDAO.deleteByStudentIdAndThesisId(studentId, thesisId);

        assertFalse(assignmentDAO.findByThesisIdAndStudentId(thesisId, studentId).isPresent());
    }

    @Test
    public void testDeleteByThesisId() {
        Long thesisId = 1L;
        Thesis thesis = new Thesis();

        thesis.setId(thesisId);
        assignment.setThesis(thesis);

        assignmentDAO.save(assignment);
        assignmentDAO.deleteByThesisId(thesisId);

        assertFalse(assignmentDAO.findByThesisId(thesisId).isPresent());
    }

    @Test
    public void testFindAllUniqueThesisIdByStudent() {

    }

    @Test
    public void testFindAllUniqueThesisIds() {

    }

    @Test
    public void testFindByStudentId() {
        Long studentId = 1L;
        Student student = new Student();

        student.setId(studentId);
        assignment.setStudent(student);

        assignmentDAO.save(assignment);

        assertEquals(assignment, assignmentDAO.findByStudentId(studentId));
    }

    @Test
    public void testFindByThesisId() {
        Long thesisId = 1L;
        Thesis thesis = new Thesis();

        thesis.setId(thesisId);
        assignment.setThesis(thesis);

        assignmentDAO.save(assignment);

        assertEquals(assignment, assignmentDAO.findByThesisId(thesisId));
    }

    @Test
    public void testFindByThesisIdAndStudentId() {
        Long studentId = 1L;
        Long thesisId = 2L;
        Student student = new Student();
        Thesis thesis = new Thesis();

        student.setId(studentId);
        thesis.setId(thesisId);

        assignment.setStudent(student);
        assignment.setThesis(thesis);

        assignmentDAO.save(assignment);

        assertEquals(assignment, assignmentDAO.findByThesisIdAndStudentId(thesisId, studentId));
    }
}
