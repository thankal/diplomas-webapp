package com.example.thesisapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String getAdminHome(Model model, Authentication authentication){
        User user = userService.getUserByUsername(authentication.getName());
        Professor prof = professorService.findProfessorByUser(user);

        String username = user.getUsername();
        String email = user.getEmail();
        String role = user.getRole().getValue();
        String fullName = prof.getFirstName() + " " + prof.getLastName();
        String speciality = prof.getSpeciality();

        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("role", role);
        model.addAttribute("fullName", fullName);
        model.addAttribute("speciality", speciality);

		
        return "professor/profile";
    }
}
