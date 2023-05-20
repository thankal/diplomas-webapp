package com.example.thesisapp.dao;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.Assert.*;


import com.example.thesisapp.model.Thesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class ThesisDAOTest {
    
    @Autowired
    private ThesisDAO thesisDAO;
    
    Thesis thesis = new Thesis();
    
    @Test
    public void testFindById() {
        Long id = 1L;

        thesis.setId(id);
        thesisDAO.save(thesis);

        thesisDAO.findById(id);
        assertEquals(thesis, thesisDAO.findById(id));
    }
}
