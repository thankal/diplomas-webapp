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

import com.example.thesisapp.model.Application;
import com.example.thesisapp.model.Assignment;
import com.example.thesisapp.model.BestAverageSelectionStrategy;
import com.example.thesisapp.model.FewestCoursesSelectionStrategy;
import com.example.thesisapp.model.Professor;
import com.example.thesisapp.model.RandomSelectionStrategy;
import com.example.thesisapp.model.SelectionStrategy;
import com.example.thesisapp.model.Student;
import com.example.thesisapp.model.Thesis;
import com.example.thesisapp.model.ThresholdSelectionStrategy;
import com.example.thesisapp.model.User;
import com.example.thesisapp.service.ApplicationService;
import com.example.thesisapp.service.AssignmentService;
import com.example.thesisapp.service.ProfessorService;
import com.example.thesisapp.service.StudentService;
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
    private StudentService studentService;
    
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AssignmentService assignmentService;
	
	@Autowired
	public ThesisController(ThesisService theThesisService) {
		thesisService = theThesisService;
	}
	

	@RequestMapping("/list")
	public String listThesis(Model theModel) {
		// useful for getting the current user for the rest 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String currentUid = userDetails.getUsername();
		User currentUser = userService.getUserByUsername(currentUid);
		
		// get all available thesis from db
		List<Thesis> theThesis = thesisService.findAll();
		
		// add to the spring model
		theModel.addAttribute("thesis", theThesis);


		
		// System.out.println(currentUser.getRole().getValue()); // TODO: del
		if (currentUser.getRole().getValue().equals("Student")) {

			Student student = studentService.findStudentByUser(currentUser);
			List<Long> appliedThesisIds = applicationService.getApplicationIdsByStudentId(student.getId());
			// System.out.println(appliedThesisIds); // TODO: del
			theModel.addAttribute("appliedThesisIds", appliedThesisIds);
		}

		else {
			Professor currentProfessor = professorService.findProfessorByUser(currentUser);
			theModel.addAttribute("currentProfessor", currentProfessor);
		}
		
		return "thesis/list-thesis";
	}
	
	@RequestMapping("/detail")
	public String detailThesis(Model theModel, @RequestParam("thesisId") long thesisId){
									
		// useful for getting the current user for the rest 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String currentUid = userDetails.getUsername();
		User currentUser = userService.getUserByUsername(currentUid);
		
		// get the selected thesis from db
		Thesis theThesis = thesisService.findById(thesisId);

		// add to the spring model
		theModel.addAttribute("thesis", theThesis);

		List<Student> studentsApplied = applicationService.getStudentsApplied(thesisId);
		theModel.addAttribute("studentsApplied", studentsApplied);

		theModel.addAttribute("numOfApplications", studentsApplied.size());

		// get the assigned student if there is one
		Student studentAssigned = assignmentService.getStudentAssigned(thesisId).orElse(null);
		theModel.addAttribute("studentAssigned", studentAssigned);

		
		return "thesis/detail-thesis";
	}
	
	@RequestMapping("/assign")
	public String assignThesis(Model model, @RequestParam("thesisId") long thesisId){
									
		// useful for getting the current user for the rest 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String currentUid = userDetails.getUsername();
		User currentUser = userService.getUserByUsername(currentUid);

		
		// get the selected thesis from db
		Thesis theThesis = thesisService.findById(thesisId);

		// add to the spring model
		model.addAttribute("thesis", theThesis);

		List<Student> studentsApplied = applicationService.getStudentsApplied(thesisId);
		model.addAttribute("studentsApplied", studentsApplied);
		model.addAttribute("numOfApplications", studentsApplied.size());


		// get the assigned student if there is one
		Student studentAssigned = assignmentService.getStudentAssigned(thesisId).orElse(null);
		model.addAttribute("studentAssigned", studentAssigned);

		
		return "thesis/assign-thesis";
	}

	// User selects a strategy and confirms the assignment
	@PostMapping("/assign")
	public String confirmAssignmentThesis(Model theModel, @RequestParam("thesisId") long thesisId,
															@RequestParam("strategy") String strategyOption,
															@RequestParam(value="th1", required=false) Double th1,
															@RequestParam(value="th2", required=false) Integer th2){
									
		// useful for getting the current user for the rest 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String currentUid = userDetails.getUsername();
		User currentUser = userService.getUserByUsername(currentUid);

        Professor currentProfessor = professorService.findProfessorByUser(currentUser);

		SelectionStrategy strategy;
		if (strategyOption.equals("random")) {
			strategy = new RandomSelectionStrategy();
		}
		else if (strategyOption.equals("average")) {
			strategy = new BestAverageSelectionStrategy();
		}
		else if (strategyOption.equals("fewest")) {
			strategy = new FewestCoursesSelectionStrategy();
		}
		else if (strategyOption.equals("threshold")) {
			strategy = new ThresholdSelectionStrategy(th1, th2);
		}
		else {
			// throw error
			return "redirect:/thesis/list";
		}

		// get the list of candidates that applied and get the winner
		List<Student> studentsApplied = applicationService.getStudentsApplied(thesisId);
		Student selectedStudent = strategy.selectCandidate(studentsApplied);


		// get the current thesis from db
		Thesis theThesis = thesisService.findById(thesisId);

		// make sure the proffesor is the one that created the thesis
		if (currentProfessor == theThesis.getProfessor()) {

			if(assignmentService.assignmentExists(thesisId, selectedStudent.getId())){
				// System.out.println("Assignment for this thesis already exists!");
				theModel.addAttribute("assignmentExists", "There is already an assignment for this thesis!");
				// redirect back to the assignment page
				return "redirect:/thesis/assign?thesisId=" + thesisId;
			}

			// ..and then make the new updated assignment
			Assignment theAssignment = new Assignment();
			theAssignment.setStudent(selectedStudent);
			theAssignment.setThesis(theThesis);
			assignmentService.save(theAssignment);
			applicationService.cancelAllAplications();
		}
		else {
			// not allowed TODO: maybe throw a forbidden message?
			return "redirect:/thesis/list";
		}

		
        // redirect back to the assignment page
		return "redirect:/thesis/assign?thesisId=" + thesisId;
	}

	@RequestMapping("/cancelAssignment")
	public String cancelAssignmentThesis(Model theModel, @RequestParam("thesisId") long thesisId){
		// useful for getting the current user for the rest 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String currentUid = userDetails.getUsername();
		User currentUser = userService.getUserByUsername(currentUid);
        Professor currentProfessor = professorService.findProfessorByUser(currentUser);

        Thesis thesis = thesisService.findById(thesisId);

		// make sure the proffesor is the one that created the thesis
		if (currentProfessor == thesis.getProfessor()) {
			// cancel existing assignment
			if (assignmentService.assignmentExists(thesisId)) {
				assignmentService.cancelAssignmentByThesisId(thesisId);
			}
		}
		
		return "redirect:/thesis/assign?thesisId=" + thesisId;
	}
	
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Thesis theThesis = new Thesis();
		
		theModel.addAttribute("thesis", theThesis);
		
		return "thesis/thesis-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("thesisId") long theId,
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

		// useful for getting the current user for the rest 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String currentUid = userDetails.getUsername();
		User currentUser = userService.getUserByUsername(currentUid);

        // TODO: maybe add a check if the user is a professor
        Professor currentProfessor = professorService.findProfessorByUser(currentUser);

        theThesis.setProfessor(currentProfessor);
		thesisService.save(theThesis);
		
        // redirect back to the list
		return "redirect:/thesis/list";
	}
	
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("thesisId") int theId) {
		
		// delete the thesis
		thesisService.deleteById(theId);
		
		return "redirect:/thesis/list";
		
	}

	@RequestMapping("/apply")
	public String apply(@RequestParam("thesisId") int theId, Model model) {
		// useful for getting the current user for the rest 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String currentUid = userDetails.getUsername();
		User currentUser = userService.getUserByUsername(currentUid);

		
		// apply to the thesis
        Thesis thesis = thesisService.findById(theId);
        Student student = studentService.findStudentByUser(currentUser);

		if(applicationService.applicationExists(student.getId(), thesis.getId())){
			System.out.println("Application already exists!");
            model.addAttribute("applicationExists", "You have already applied to this thesis!");
			return "redirect:/thesis/list";
        }
        Application application = new Application();
        application.setStudent(student);
        application.setThesis(thesis);
        applicationService.saveApplication(application);
		
		
		return "redirect:/thesis/list";
		
	}
	
	@RequestMapping("/cancel")
	public String cancelApplication(@RequestParam("thesisId") int theId, Model model) {
		// useful for getting the current user for the rest 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String currentUid = userDetails.getUsername();
		User currentUser = userService.getUserByUsername(currentUid);

		
		// cancel application to the thesis
        Thesis thesis = thesisService.findById(theId);
        Student student = studentService.findStudentByUser(currentUser);

		// check if the application exists
		if(!applicationService.applicationExists(student.getId(), thesis.getId())){
			model.addAttribute("applicationExists", "You haven't applied to this thesis yet!");
			return "redirect:/thesis/list";
        }
		
		Application application = applicationService.getApplicationByStudentIdAndThesisId(student.getId(), thesis.getId());

        applicationService.cancelApplication(application.getId());
		
		
		return "redirect:/thesis/list";
		
	}
}



















