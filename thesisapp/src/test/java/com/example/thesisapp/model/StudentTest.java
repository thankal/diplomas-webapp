package com.example.thesisapp.model;

import org.junit.Test;
import org.junit.Before; 
import org.junit.Assert;
import org.junit.jupiter.api.Assertions; 
    
public class StudentTest {
    private Student student ;


    @Before
    public void setup(){
        student = new Student();
    }
        
    @Test
    public void testSetGetId() {
        Long id = 1L;
        student.setId(id);
        Assert.assertEquals(id, student.getId());    
    }

    @Test
    public void testSetGetFirstName(){
        String firstName = "George";
        student.setFirstName(firstName);
        Assert.assertEquals(firstName,student.getFirstName());
    }
    
    @Test
    public void testSetGetLastName(){
        String lastName = "Nick";
        student.setLastName(lastName);
        Assert.assertEquals(lastName,student.getLastName());
    }

    @Test
    public void testSetGetYearOfStudies(){
        int yearOfStudies = 5;
        student.setYearOfStudies(yearOfStudies);
        Assert.assertEquals(yearOfStudies,student.getYearOfStudies());
    }

    @Test
    public void testSetGetAverageGrade(){
        Double averageGrade = 1.0;
        student.setAverageGrade(averageGrade);
        Assertions.assertEquals(averageGrade,student.getAverageGrade());
    }

    @Test
    public void testSetGetRemainingCourses(){
        int remainingCourses = 2;
        student.setAverageGrade(remainingCourses);
        Assertions.assertEquals(remainingCourses,student.getAverageGrade());
    }
    
    @Test 
    public void testSetGetUser(){
        User user = new User();
        student.setUser(user);
        Assertions.assertEquals(user, student.getUser());
    }

}
    