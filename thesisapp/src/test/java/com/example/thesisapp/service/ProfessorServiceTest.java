package com.example.thesisapp.service;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.thesisapp.dao.ProfessorDAO;
import com.example.thesisapp.model.Professor;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class ProfessorServiceTest {

    @TestConfiguration
    static class ProfessorServiceImplTestContextConfiguration {
        @Bean
        public ProfessorService professorService() {
            return new ProfessorServiceImpl();
        }
    }

    @Autowired
    private ProfessorService professorService;

    @MockBean
    private ProfessorDAO professorDAO;

    @Test
    public void testProfessorDAOJpaImplIsNotNull() {
        Assertions.assertNotNull(professorService);
    }

    @Test
    public void testFindById() {
        Professor professor = new Professor();
        Mockito.when(professorDAO.findById(1L)).thenReturn(Optional.of(professor));
        Professor foundProfessor = professorService.findById(1L);
        Assertions.assertEquals(professor, foundProfessor);
    }

    @Test
    public void testFindProfessorByUser() {
        Professor professor = new Professor();
        Mockito.when(professorDAO.findByUser(Mockito.any())).thenReturn(Optional.of(professor));
        Professor foundProfessor = professorService.findProfessorByUser(new Professor().getUser());
        Assertions.assertEquals(professor, foundProfessor);
    }

    @Test
    public void testSaveProfessor() {
        Professor professor = new Professor();
        Mockito.when(professorDAO.save(professor)).thenReturn(professor);
        professorService.saveProfessor(professor);
        Mockito.verify(professorDAO).save(professor);
    }
}
