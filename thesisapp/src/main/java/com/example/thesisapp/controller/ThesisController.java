package com.example.thesisapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.thesisapp.model.Application;
import com.example.thesisapp.model.Assignment;
import com.example.thesisapp.model.BestAverageSelectionStrategy;
import com.example.thesisapp.model.Evaluation;
import com.example.thesisapp.model.EvaluationFormula;
import com.example.thesisapp.model.FewestCoursesSelectionStrategy;
import com.example.thesisapp.model.Professor;
import com.example.thesisapp.model.RandomSelectionStrategy;
import com.example.thesisapp.model.SelectionStrategy;
import com.example.thesisapp.model.StandardEvaluationFormula;
import com.example.thesisapp.model.Student;
import com.example.thesisapp.model.Thesis;
import com.example.thesisapp.model.ThresholdSelectionStrategy;
import com.example.thesisapp.model.User;
import com.example.thesisapp.service.ApplicationService;
import com.example.thesisapp.service.AssignmentService;
import com.example.thesisapp.service.EvaluationService;
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
    private EvaluationService evaluationService;
	
	@Autowired
	public ThesisController(ThesisService theThesisService) {
		thesisService = theThesisService;
	}
	
	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User) authentication.getPrincipal();
	}

	@RequestMapping("/list")
	public String listThesis(Model theModel) {
		User currentUser = getCurrentUser();

		// get all available thesis from db
		List<Thesis> thesisList = thesisService.findAll();

		List<Professor> professorList = new ArrayList<Professor>();
		for (Thesis thesis : thesisList) {
			if (!professorList.contains(thesis.getProfessor())) {
				professorList.add(thesis.getProfessor());
			}
		}

		
		// add to the spring model
		theModel.addAttribute("thesis", thesisList);
		theModel.addAttribute("professors", professorList);


		if (currentUser.getRole().getValue().equals("Student")) {

			Student student = studentService.findStudentByUser(currentUser);
			List<Long> appliedThesisIds = applicationService.getApplicationIdsByStudentId(student.getId());
			List<Long> assignedThesisIds = assignmentService.getThesisIds();
			Long myAssignedThesisId = assignmentService.getThesisIdByStudent(student.getId()).orElse(null);
			theModel.addAttribute("appliedThesisIds", appliedThesisIds);
			theModel.addAttribute("assignedThesisIds", assignedThesisIds);
			theModel.addAttribute("myAssignedThesisId", myAssignedThesisId);
		}

		else {
			Professor currentProfessor = professorService.findProfessorByUser(currentUser);
			theModel.addAttribute("currentProfessor", currentProfessor);
		}
		
		return "thesis/list-thesis";
	}
	
	@RequestMapping("/detail")
	public String detailThesis(Model theModel, @RequestParam("thesisId") long thesisId){

		User currentUser = getCurrentUser();
	
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

		if (currentUser.getRole().getValue().equals("Professor")) {
			Professor currentProfessor = professorService.findProfessorByUser(currentUser);
			theModel.addAttribute("currentProfessor", currentProfessor);
			Evaluation evaluation = evaluationService.getEvaluationForThesis(thesisId).orElse(null);
			theModel.addAttribute("evaluation", evaluation);

		}
		else if (currentUser.getRole().getValue().equals("Student")){
			Student student = studentService.findStudentByUser(currentUser);
			Long myAssignedThesisId = assignmentService.getThesisIdByStudent(student.getId()).orElse(null);
			theModel.addAttribute("myAssignedThesisId", myAssignedThesisId);
			Evaluation evaluation = evaluationService.getEvaluationForThesis(thesisId).orElse(null);
			theModel.addAttribute("evaluation", evaluation);

		}
	
		
		return "thesis/detail-thesis";
	}
	
	@RequestMapping("/assign")
	public String assignThesis(Model model, @RequestParam("thesisId") long thesisId){
									
		User currentUser = getCurrentUser();

		if (currentUser.getRole().getValue().equals("Professor")) {
			Professor currentProfessor = professorService.findProfessorByUser(currentUser);
			model.addAttribute("currentProfessor", currentProfessor);
		}
		else {
			return "redirect:/thesis/list";
		}


		
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
									

		User currentUser = getCurrentUser();


		Professor currentProfessor;
		if (currentUser.getRole().getValue().equals("Professor")) {
			currentProfessor = professorService.findProfessorByUser(currentUser);
			theModel.addAttribute("currentProfessor", currentProfessor);
		}
		else {
			return "redirect:/thesis/list";
		}

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
			if (th1 == null || th2 == null) {
				// throw error
				return "redirect:/thesis/list";
			}
			strategy = new ThresholdSelectionStrategy(th1, th2);
		}
		else {
			// throw error
			return "redirect:/thesis/list";
		}

		// get the list of candidates that applied and get the winner
		List<Student> studentsApplied = applicationService.getStudentsApplied(thesisId);
		Student selectedStudent;
		if (!studentsApplied.isEmpty()) {
			selectedStudent = strategy.selectCandidate(studentsApplied);
		}
		else {
			// no students applied
			return "redirect:/thesis/assign?thesisId=" + thesisId;
		}


		// get the current thesis from db
		Thesis theThesis = thesisService.findById(thesisId);

		// make sure the proffesor is the one that created the thesis
		if (currentProfessor == theThesis.getProfessor()) {

			if(assignmentService.assignmentExists(thesisId, selectedStudent.getId())){
				theModel.addAttribute("assignmentExists", "There is already an assignment for this thesis!");
				// redirect back to the assignment page
				return "redirect:/thesis/assign?thesisId=" + thesisId;
			}

			// ..and then make the new updated assignment
			Assignment theAssignment = new Assignment();
			theAssignment.setStudent(selectedStudent);
			theAssignment.setThesis(theThesis);
			assignmentService.save(theAssignment);

			// also cancel all other applications for this thesis
			applicationService.cancelApplicationsForThesis(thesisId);

			// and all applications for this student
			applicationService.cancelApplicationsByStudent(selectedStudent.getId());
		}
		else {
			return "redirect:/thesis/list";
		}

		
        // redirect back to the assignment page
		return "redirect:/thesis/assign?thesisId=" + thesisId;
	}

	@RequestMapping("/evaluate")
	public String evaluateThesis(Model model, @RequestParam("thesisId") long thesisId){
									

		User currentUser = getCurrentUser();

		Professor currentProfessor;
		if (currentUser.getRole().getValue().equals("Professor")) {
			currentProfessor = professorService.findProfessorByUser(currentUser);
			model.addAttribute("currentProfessor", currentProfessor);
		}
		else {
			return "redirect:/thesis/list";
		}
		
		// get the selected thesis from db
		Thesis theThesis = thesisService.findById(thesisId);

		// add to the spring model
		model.addAttribute("thesis", theThesis);

		// make sure the proffesor is the one that created the thesis
		if (currentProfessor == theThesis.getProfessor()) {

			// get the assigned student if there is one
			Student studentAssigned = assignmentService.getStudentAssigned(thesisId).orElseThrow(
				()-> new RuntimeException("ASSIGNMENT_NOT_FOUND")
			);
			model.addAttribute("studentAssigned", studentAssigned);

			Evaluation evaluation = evaluationService.getEvaluationForThesis(thesisId).orElse(null);
			model.addAttribute("evaluation", evaluation);

		}
		else {
			return "redirect:/thesis/list";
		}


		
		return "thesis/evaluate-thesis";
	}

	// User selects a strategy and confirms the assignment
	@PostMapping("/evaluate")
	public String saveEvaluationThesis(Model theModel, @RequestParam("thesisId") long thesisId,
															@RequestParam("formula") String formulaOption,
															@RequestParam(value="implementation") double implementationGrade,
															@RequestParam(value="report") double reportGrade,
															@RequestParam(value="presentation") double presentationGrade){
									

		User currentUser = getCurrentUser();


		Professor currentProfessor;
		if (currentUser.getRole().getValue().equals("Professor")) {
			currentProfessor = professorService.findProfessorByUser(currentUser);
			theModel.addAttribute("currentProfessor", currentProfessor);
		}
		else {
			return "redirect:/thesis/list";
		}



		// get the current thesis from db
		Thesis theThesis = thesisService.findById(thesisId);

		// make sure the proffesor is the one that created the thesis
		if (currentProfessor == theThesis.getProfessor()) {
			EvaluationFormula formula;
			if (formulaOption.equals("standard")) {
				formula = new StandardEvaluationFormula();
			}

			else {
				// throw error
				return "redirect:/thesis/list";
			}
			
			double totalGrade;
			totalGrade = formula.calculateTotalGrade(implementationGrade, reportGrade, presentationGrade);

			Assignment theAssignment = assignmentService.getAssignmentByThesisId(thesisId).orElseThrow(
				()-> new RuntimeException("ASSIGNMENT_NOT_FOUND")
			);

			// ..and then save the evaluation
			Evaluation theEvaluation = new Evaluation();
			theEvaluation.setAssignment(theAssignment);
			theEvaluation.setImplementation(implementationGrade);
			theEvaluation.setReport(reportGrade);
			theEvaluation.setPresentation(presentationGrade);
			theEvaluation.setTotal(totalGrade);
			evaluationService.save(theEvaluation);

		}
		else {
			return "redirect:/thesis/list";
		}

		
        // redirect back to the assignment page
		return "redirect:/thesis/evaluate?thesisId=" + thesisId;
	}

	@RequestMapping("/cancelAssignment")
	public String cancelAssignmentThesis(Model theModel, @RequestParam("thesisId") long thesisId){

		User currentUser = getCurrentUser();

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

		User currentUser = getCurrentUser();


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

		User currentUser = getCurrentUser();

		
		// apply to the thesis
        Thesis thesis = thesisService.findById(theId);
        Student student = studentService.findStudentByUser(currentUser);

		if(applicationService.applicationExists(student.getId(), thesis.getId())){
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

		User currentUser = getCurrentUser();

		
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



















