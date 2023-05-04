package com.example.thesisapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.thesisapp.model.User;
import com.example.thesisapp.model.Student;
import com.example.thesisapp.model.Professor;
import com.example.thesisapp.service.UserService;
import com.example.thesisapp.service.StudentService;
import com.example.thesisapp.service.ProfessorService;
import com.example.thesisapp.controller.UserForm;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Autowired
    ProfessorService professorService;

    @RequestMapping("/login")
    public String login(){
        return "auth/signin";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("userForm", new UserForm());
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("userForm") UserForm userForm, Model model){
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setEmail(userForm.getEmail());
        user.setRole(userForm.getRole());
        
        if(userService.isUserPresent(user)){
            model.addAttribute("successMessage", "User already registered!");
            return "auth/signin";
        }
        
        userService.saveUser(user);
        if (user.getRole().getValue().equals("Student")) {
            Student student = new Student();
            student.setFirstName(userForm.getFirstName());
            student.setLastName(userForm.getLastName());
            student.setYearOfStudies(userForm.getYearOfStudies());
            student.setRemainingCourses(userForm.getCoursesRemaining());
            student.setAverageGrade(userForm.getAvgGrade());
            student.setUser(user);
            studentService.saveStudent(student);
        }
        else if (user.getRole().getValue().equals("Professor")) {
            Professor professor = new Professor();
            professor.setFirstName(userForm.getFirstName());
            professor.setLastName(userForm.getLastName());
            professor.setSpeciality(userForm.getSpeciality());
            professor.setUser(user);
            professorService.saveProfessor(professor);
        }


        model.addAttribute("successMessage", "User registered successfully!");

        return "auth/signin";
    }
}
