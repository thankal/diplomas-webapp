package com.example.thesisapp.dao;

import org.junit.Test;
import org.junit.Before;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;

import com.example.thesisapp.model.User;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class UserDAOTest {
    
    @Autowired
    private UserDAO userDAO; 

    @Before 
    public void setUp() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        userDAO.save(user);
    }  

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("username");
        
        Optional<User> foundUser = userDAO.findByUsername("username");

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    public void testFindByNoneExistingUsername() {
        Optional<User> foundUser = userDAO.findByUsername("nonexistingusername");

        assertFalse(foundUser.isPresent());
    }
}
