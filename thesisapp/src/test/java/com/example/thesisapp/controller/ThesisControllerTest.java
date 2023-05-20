package com.example.thesisapp.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.ui.Model;

import com.example.thesisapp.model.Role;
import com.example.thesisapp.model.Student;
import com.example.thesisapp.model.Thesis;
import com.example.thesisapp.model.User;
import com.example.thesisapp.service.ApplicationService;
import com.example.thesisapp.service.AssignmentService;
import com.example.thesisapp.service.ProfessorService;
import com.example.thesisapp.service.StudentService;
import com.example.thesisapp.service.ThesisService;
import com.example.thesisapp.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ThesisControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;

    @Mock
    private ThesisService thesisService;
    
    @Mock
    private StudentService studentService;
    
    @Mock
    private ApplicationService applicationService;
    
    @Mock
    private AssignmentService assignmentService;
    
    @Mock
    private ProfessorService professorService;
    
    @InjectMocks
    @Autowired
    private ThesisController thesisController;

    @BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
    }
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}

	@Test
	void testThesisControllerIsNotNull() {
		Assertions.assertNotNull(thesisController);
	}

    @WithMockUser(username = "user123", roles = "Student", value = "asdf", password = "asdf" )
    @Test
    public void testListThesisAsStudent() throws Exception{
        // Mock the current user
        Authentication authentication = Mockito.mock(Authentication.class);
        User mockUser = new User();
        Role mockRole = Role.STUDENT;
        mockUser.setRole(mockRole);
        Mockito.when(authentication.getPrincipal()).thenReturn(mockUser);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        // Mock the required service methods
        List<Thesis> mockThesisList = new ArrayList<>();
        mockThesisList.add(new Thesis());
        mockThesisList.add(new Thesis());
        Mockito.when(thesisService.findAll()).thenReturn(mockThesisList);

        Student mockStudent = new Student();
        mockStudent.setId(1L);
        Mockito.when(studentService.findStudentByUser(mockUser)).thenReturn(mockStudent);

        List<Long> mockAppliedThesisIds = new ArrayList<>();
        mockAppliedThesisIds.add(1L);
        Mockito.when(applicationService.getApplicationIdsByStudentId(mockStudent.getId())).thenReturn(mockAppliedThesisIds);

        List<Long> mockAssignedThesisIds = new ArrayList<>();
        mockAssignedThesisIds.add(2L);
        Mockito.when(assignmentService.getThesisIds()).thenReturn(mockAssignedThesisIds);

        Long mockAssignedThesisId = 2L;
        Mockito.when(assignmentService.getThesisIdByStudent(mockStudent.getId())).thenReturn(Optional.of(mockAssignedThesisId));

        mockMvc.perform(get("/thesis/list"))
                .andExpect(status().isFound())
                .andExpect(view().name("thesis/list-thesis"))
                .andExpect(model().attributeExists("thesis"))
                .andExpect(model().attributeExists("professors"))
                .andExpect(model().attributeExists("appliedThesisIds"))
                .andExpect(model().attributeExists("assignedThesisIds"))
                .andExpect(model().attributeExists("myAssignedThesisId"));

        // Verify that the service methods were called
        verify(thesisService).findAll();
        verify(studentService).findStudentByUser(mockUser);
        verify(applicationService).getApplicationIdsByStudentId(mockStudent.getId());
        verify(assignmentService).getThesisIds();
        verify(assignmentService).getThesisIdByStudent(mockStudent.getId());

    }


    @Test
    @WithMockUser(username = "professor1", roles = "Professor")
    void testListThesisAsProfessor() throws Exception {
        // Prepare mock data
        Model model = mock(Model.class);
        List<Thesis> mockThesisList = new ArrayList<>();
        // Add mock thesis to the list
        // ...

        when(thesisService.findAll()).thenReturn(mockThesisList);
        // Mock other necessary calls

        // Perform the request
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/thesis/list/"));

        // Verify the response
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("thesis/list-thesis"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("thesis", "professors", "currentProfessor"));

        // Verify that the service methods were called
        verify(thesisService).findAll();
        // Verify other necessary calls
    }

    @Test
    public void testApply() {

    }

    @Test
    public void testAssignThesis() {

    }

    @Test
    public void testCancelApplication() {

    }

    @Test
    public void testCancelAssignmentThesis() {

    }

    @Test
    public void testConfirmAssignmentThesis() {

    }

    @Test
    public void testDelete() {

    }

    @Test
    public void testDetailThesis() {

    }

    @Test
    public void testEvaluateThesis() {

    }

    @Test
    public void testListThesis() {

    }

    @Test
    public void testSaveEvaluationThesis() {

    }

    @Test
    public void testSaveThesis() {

    }

    @Test
    public void testShowFormForAdd() {

    }

    @Test
    public void testShowFormForUpdate() {

    }

}

