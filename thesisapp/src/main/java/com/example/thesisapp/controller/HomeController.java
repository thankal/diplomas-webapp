package com.example.thesisapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/about")
    public String about() {
        return "about";
    }
    
}
