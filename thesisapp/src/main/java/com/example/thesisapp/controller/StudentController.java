package com.example.thesisapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.example.thesisapp.model.Student;
import com.example.thesisapp.model.User;
import com.example.thesisapp.service.UserService;
import com.example.thesisapp.service.StudentService;


@Controller
public class StudentController {
    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @RequestMapping("/student/profile")
    public String getUserHome(Model model, Authentication authentication){

        User user = userService.getUserByUsername(authentication.getName());
        Student student = studentService.findStudentByUser(user);

        String username = user.getUsername();
        String email = user.getEmail();
        String role = user.getRole().getValue();
        String fullName = student.getFirstName() + " " + student.getLastName();
        int yearsOfStudy = student.getYearOfStudies();
        int remainingCourses = student.getRemainingCourses();
        double averageGrade = student.getAverageGrade();

        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("role", role);
        model.addAttribute("fullName", fullName);
        model.addAttribute("yearsOfStudy", Integer.toString(yearsOfStudy));
        model.addAttribute("remainingCourses", Integer.toString(remainingCourses));
        model.addAttribute("averageGrade", averageGrade);

		
        return "student/profile";
    }
}
