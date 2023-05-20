package com.example.thesisapp.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;    
    
public class AssignmentTest {

    private Assignment assignment;

    @Before
    public void setup(){
        assignment = new Assignment();

    }
        
    @Test
    public void testId() {
        Long id = 1L;
        assignment.setId(id);
        Assert.assertEquals(id, assignment.getId());
        
    }

    @Test
    public void testStudent(){
        Student student = new Student();
        assignment.setStudent(student);
        Assert.assertEquals(student, assignment.getStudent());
    }

    @Test
    public void testThesis() {
        Thesis thesis = new Thesis();
        assignment.setThesis(thesis);
        Assert.assertEquals(thesis, assignment.getThesis());
    }

}
    