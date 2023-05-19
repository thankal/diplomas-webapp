package com.example.thesisapp.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ApplicationTest {
    
    private Application application;
    
    @Before
    public void setUp() {
        application = new Application();
    }
    
    @Test
    public void testId() {
        Long id = 1L;
        application.setId(id);
        Assert.assertEquals(id, application.getId());
    }
    
    @Test
    public void testStudent() {
        Student student = new Student();
        application.setStudent(student);
        Assert.assertEquals(student, application.getStudent());
    }
    
    @Test
    public void testThesis() {
        Thesis thesis = new Thesis();
        application.setThesis(thesis);
        Assert.assertEquals(thesis, application.getThesis());
    }
    
}

