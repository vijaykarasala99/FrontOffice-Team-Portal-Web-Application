package com.vijayit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vijayit.bindingclasses.LoginForm;
import com.vijayit.bindingclasses.SignupForm;
import com.vijayit.bindingclasses.UnlockForm;
import com.vijayit.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String loginPage(@ModelAttribute("form") LoginForm form, Model model) {

		return "login";

	}
	
	@PostMapping("/userLogin")
	public String userLogin(@ModelAttribute("form") LoginForm form, Model model) {
		
		System.out.println(form);
		
		String login = userService.login(form);
		
		if(login.contains("Success")) {
			
			return "redirect:/dashboard";		
		}else {
			model.addAttribute("msg", login);
		}
		
		return "login";
	}

	@GetMapping("/signup")
	public String signUpPage(Model model) {

		model.addAttribute("msg", new SignupForm());

		return "signup";
	}

	@PostMapping("/usersignup")
	public String userSignUp(SignupForm signup, Model model) {

		boolean signUp2 = userService.signUp(signup);
		if (signUp2) {
			model.addAttribute("key", "Your Registration is Success, Check Your Email");

		} else {
			model.addAttribute("keys", "You Entered Duplicate Email");
		}

		model.addAttribute("msg", new SignupForm());

		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam("staffEmail") String staffEmail, Model model) {

		System.out.println(staffEmail);
		UnlockForm form = new UnlockForm();
		form.setStaffEmail(staffEmail);

		model.addAttribute("unlocks", form);

		return "unlock";
	}

	@PostMapping("/unlockAccount")
	public String unlockAccount(@ModelAttribute("unlocks") UnlockForm unlocks, Model model) {

		System.out.println(unlocks);
		if (unlocks.getNewPwd().equals(unlocks.getConfirmPwd())) {

			boolean status = userService.unlock(unlocks);

			if (status) {
				model.addAttribute("unlocked", "Your account is unlocked Successfully");
			} else {
				model.addAttribute("locked", "You entered wrong Temporary Password ");
			}
		} else {
			model.addAttribute("msg", "New Password and Confirm Password should be same");
		}
		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {

		return "forgotPwd";
	}
	
	@PostMapping("/forgotPwd")
	public String userForgotPwd(@RequestParam("staffEmail") String staffEmail, Model model) {
		
		System.out.println(staffEmail);
		
		boolean status = userService.forgotPwd(staffEmail);
		if(status) {
		
			model.addAttribute("PwdSent","Your Password is Send to your email successfully");
		}else {
		
		model.addAttribute("PwdnotSent","email not sent there is problem ");
		}
		return "forgotPwd";

		
	}

}
