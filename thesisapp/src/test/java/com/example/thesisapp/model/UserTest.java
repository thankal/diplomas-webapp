package com.example.thesisapp.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
  
    
public class UserTest {
    private User user;

    @Before
    public void setup(){
        user = new User();
    }
        
    @Test
    public void testSetGetEmail() {
        String email = "gmailcom";
        user.setEmail(email);
        Assert.assertEquals(email, user.getEmail());
    }

    @Test
    public void testSetGetUserName(){
        String username = "Nas";
        user.setUsername(username);
        Assert.assertEquals(username, user.getUsername());
    }
    

    @Test
    public void testSetGetPassword(){
        String password ="abc";
        user.setPassword(password);
        Assert.assertEquals(password, user.getPassword());
    }

    @Test
    public void testSetGetId() {
        Long id = 1L;
        user.setId(id);
        Assert.assertEquals(id, user.getId());    
    }

    @Test
    public void testisAccountNonExpired(){
        User account = new User();
        assertTrue(account.isAccountNonExpired());
        
    }

    @Test
    public void testisAccountNonLocked(){
        User account = new User();
        assertTrue(account.isAccountNonLocked());
    }

    @Test
    public void testisCredentialsNonExpired(){
        User account = new User();
        assertTrue(account.isCredentialsNonExpired());
    }

    @Test
    public void testisEnabled(){
        User account = new User();
        assertTrue(account.isEnabled());
    }

}
    