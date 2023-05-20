package com.example.thesisapp.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;    
    
public class ProfessorTest {

    private Professor professor;

    @Before
    public void setup(){
        professor = new Professor();    
    }
        
    @Test
    public void testSetGetId() {
        Long id = 1L;
        professor.setId(id);
        Assert.assertEquals(id, professor.getId());   
    }

    public void testSetGetFirstName(){
        String firstName = "dim";
        professor.setFirstName(firstName);
        Assert.assertEquals(firstName,professor.getFirstName());
    }

    public void testSetGetLastName(){
        String lastName = "dimi";
        professor.setLastName(lastName);
        Assert.assertEquals(lastName, professor.getLastName());   
    }

    public void testSetGetSpeciality(){
        String speciality = "soft";
        professor.setSpeciality(speciality);
        Assert.assertEquals(speciality, professor.getSpeciality());
    }

    public void testSetGetUser(){
        User user = new User();
        professor.setUser(user);
        Assert.assertEquals(user, professor.getUser());
    }

    
}
    