package com.example.thesisapp.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;    
    
public class ThesisTest {
    
    private Thesis thesis;

    @Before
    public void setup(){
         thesis = new Thesis();

    }
        
    @Test
    public void testSetGetId() {
        Long id = 1L;
        thesis.setId(id);
        Assert.assertEquals(id, thesis.getId());    
        }
        
    @Test
    public void testSetGetTitle(){
        String title = "Soft";
        thesis.setTitle(title);
        Assert.assertEquals(title, thesis.getTitle());
    }
    
    
    @Test
    public void testSetGetObjectives(){
        String objectives = "objectives";
        thesis.setObjectives(objectives);
        Assert.assertEquals(objectives, thesis.getObjectives());
    }

    @Test
    public void testSetGetProfessor(){
        Professor professor = new Professor();
        thesis.setProfessor(professor);
        Assert.assertEquals(professor, thesis.getProfessor());
    }
    
    
}
    