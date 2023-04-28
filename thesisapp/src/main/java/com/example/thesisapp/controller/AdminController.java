package com.example.thesisapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/professor/dashboard")
    public String getAdminHome(){
        return "professor/dashboard";
    }
}
