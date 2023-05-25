package com.vijayit.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vijayit.bindingclasses.DashboardForm;
import com.vijayit.bindingclasses.EnquiryForm;
import com.vijayit.bindingclasses.EnquirySearchCriteria;
import com.vijayit.entity.StudentEntity;
import com.vijayit.repository.StudentRepository;
import com.vijayit.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private EnquiryService enqService;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {

		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer staffId = (Integer) session.getAttribute("staffId");

		DashboardForm dashboardData = enqService.getDashboardData(staffId);

		model.addAttribute("dataOfDashboard", dashboardData);

		return "dashboard";
	}

	@GetMapping("/addenquiry")
	public String addEnquiryPage(Model model) {

		init(model);

		EnquiryForm form = new EnquiryForm();

		model.addAttribute("enquiryForm", form);

		return "add-enquiry";
	}

	@PostMapping("/addEnquiries")
	public String addEnquiries(@ModelAttribute EnquiryForm enquiryForm, Model model) {

		// System.out.println(enquiryForm);
		boolean saveEnquiry = enqService.saveEnquiry(enquiryForm);

		if (saveEnquiry) {
			model.addAttribute("success", "Details added Succesfully");
		} else {
			model.addAttribute("error", "Details not added");
		}

		return "add-enquiry";

	}

	@GetMapping("/viewenquiry")
	public String viewEnquiryPage(@ModelAttribute("form") EnquiryForm form, Model model) {

		init(model);

		List<StudentEntity> studentDetails = enqService.getStudentDetails();

		model.addAttribute("details", studentDetails);

		return "view-enquiries";
	}

	private void init(Model model) {
		List<String> courseNames = enqService.getCourseNames();

		List<String> enqStatus = enqService.getEnqStatus();

		model.addAttribute("coursenames", courseNames);

		model.addAttribute("statuses", enqStatus);
	}

	@GetMapping("/update")
	public String upDate(@RequestParam("enquiryId") Integer enquiryId, Model model) {
		// get employee from the service
		
		init(model);

		Optional<StudentEntity> findById = studentRepo.findById(enquiryId);

		if (findById.isPresent()) {
			StudentEntity form = findById.get();

			// set employee as a model attribute to pre-populate the form
			
			model.addAttribute("enquiryForm", form);

		}
		return "add-enquiry";
	}
	

	@GetMapping("/filter-enquiries")
	public String getFilteredEnquiries(@RequestParam String cname, @RequestParam String status,
			@RequestParam String mode, Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourse(cname);
		criteria.setClassMode(mode);
		criteria.setStatus(status);
		
		System.out.println(criteria);
		
		Integer staffId = (Integer) session.getAttribute("staffId");

		List<StudentEntity> enquiries = enqService.getFilteredEnquiries(criteria, staffId);
		
		model.addAttribute("details", enquiries);
		
		return "filteredEnquiries";
	}
}
