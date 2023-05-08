package com.example.thesisapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.thesisapp.model.Professor;
import com.example.thesisapp.model.Thesis;
import com.example.thesisapp.model.User;
import com.example.thesisapp.service.ProfessorService;
import com.example.thesisapp.service.ThesisService;
import com.example.thesisapp.service.UserService;

@Controller
@RequestMapping("/thesis")
public class ThesisController {

	@Autowired
	private ThesisService thesisService;

	@Autowired
    private UserService userService;

	@Autowired
    private ProfessorService professorService;
	
	@Autowired
	public ThesisController(ThesisService theThesisService) {
		thesisService = theThesisService;
	}
	
	// add mapping for "/list"

	@RequestMapping("/list")
	public String listEmployees(Model theModel) {
		
		// get employees from db
		List<Thesis> theThesis = thesisService.findAll();
		
		// add to the spring model
		theModel.addAttribute("thesis", theThesis);
		
		return "thesis/list-thesis";
	}
	
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Thesis theThesis = new Thesis();
		
		theModel.addAttribute("thesis", theThesis);
		
		return "thesis/thesis-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("thesisId") int theId,
									Model theModel) {
		
		// get the thesis from the service
		Thesis theThesis = thesisService.findById(theId);
		
		// set thesis as a model attribute to pre-populate the form
		theModel.addAttribute("thesis", theThesis);
		
		
		// send over to our form
		return "thesis/thesis-form";			
	}
	
	
	@RequestMapping("/save")
	public String saveThesis(@ModelAttribute("thesis") Thesis theThesis){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUid = userDetails.getUsername();

        User currentUser = userService.getUserByUsername(currentUid);

        Professor currentProfessor = professorService.findProfessorByUser(currentUser);

        theThesis.setProfessor(currentProfessor);
		thesisService.save(theThesis);
		
        // redirect back to the list
		return "redirect:/thesis/list";
	}
	
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("thesisId") int theId) {
		
		// delete the employee
		thesisService.deleteById(theId);
		
		// redirect to /employees/list ACTION
		return "redirect:/thesis/list";
		
	}
}



















