package com.example.thesisapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.thesisapp.model.Professor;
import com.example.thesisapp.model.User;
import com.example.thesisapp.service.ProfessorService;
import com.example.thesisapp.service.UserService;

@Controller
public class ProfessorController {
    @Autowired
    UserService userService;

    @Autowired
    ProfessorService professorService;


    @RequestMapping("/professor/profile")
    public String getUserHome(Model model, Authentication authentication, @RequestParam(value="professorId", required=false) Long professorId){
        // my profile
        User user;
        Professor professor;
        if (professorId == null) {
            user = userService.getUserByUsername(authentication.getName());
            professor = professorService.findProfessorByUser(user);
        }

        // see other professor's profile
        else {
            professor = professorService.findById(professorId);
            if (professor == null) {
                throw new RuntimeException("PROFESSOR_NOT_FOUND");
            }
    
            user = professor.getUser();
        }
 
        String username = user.getUsername();
        String email = user.getEmail();
        String role = user.getRole().getValue();
        String fullName = professor.getFirstName() + " " + professor.getLastName();
        String speciality = professor.getSpeciality();


        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("role", role);
        model.addAttribute("fullName", fullName);
        model.addAttribute("speciality", speciality);

		
        return "professor/profile";
    }
}
