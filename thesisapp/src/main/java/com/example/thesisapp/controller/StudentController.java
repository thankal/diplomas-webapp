package com.example.thesisapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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
    public String getUserHome(Model model, Authentication authentication, @RequestParam(value="studentId", required=false) Long studentId){
        // my profile
        User user;
        Student student;
        if (studentId == null) {
            user = userService.getUserByUsername(authentication.getName());
            student = studentService.findStudentByUser(user);
        }

        // see other student's profile
        else {
            student = studentService.findById(studentId);
            if (student == null) {
                throw new RuntimeException("STUDENT_NOT_FOUND");
            }
    
            user = student.getUser();
        }
 
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
