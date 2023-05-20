package com.example.thesisapp.service;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.example.thesisapp.dao.ThesisDAO;
import com.example.thesisapp.model.Thesis;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
@ContextConfiguration(locations = { "test-context.xml" })
public class ThesisServiceTest {

    @TestConfiguration
    static class ThesisServiceImplTestContextConfiguration {
        @Bean
        public ThesisService thesisService() {
            return new ThesisServiceImpl();
        }
    }

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ThesisService thesisService;

    @MockBean
    private ThesisDAO thesisDAO;

    @Test
    public void testThesisDAOJpaImplIsNotNull() {
        Assertions.assertNotNull(thesisService);
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(thesisDAO).deleteById(1L);
        thesisService.deleteById(1L);
        Mockito.verify(thesisDAO).deleteById(1L);
    }

    @Test
    public void testFindAll() {
        Thesis thesis1 = new Thesis();
        thesis1.setId(1L);
        Thesis thesis2 = new Thesis();
        thesis2.setId(2L);
        
        List<Thesis> theses = Arrays.asList(thesis1, thesis2);
        Mockito.when(thesisDAO.findAll()).thenReturn(theses);
        List<Thesis> foundTheses = thesisService.findAll();
        Assertions.assertEquals(theses, foundTheses);
    }

    @Test
    public void testFindById() {
        Thesis thesis = new Thesis();
        Mockito.when(thesisDAO.findById(1L)).thenReturn(Optional.of(thesis));
        Thesis foundThesis = thesisService.findById(1L);
        Assertions.assertEquals(thesis, foundThesis);
    }

    @Test
    public void testSave() {
        Thesis thesis = new Thesis();
        Mockito.when(thesisDAO.save(thesis)).thenReturn(thesis);
        thesisService.save(thesis);
        Mockito.verify(thesisDAO).save(thesis);
    }
}
