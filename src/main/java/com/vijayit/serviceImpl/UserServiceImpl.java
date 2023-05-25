package com.vijayit.serviceImpl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vijayit.bindingclasses.LoginForm;
import com.vijayit.bindingclasses.SignupForm;
import com.vijayit.bindingclasses.UnlockForm;
import com.vijayit.entity.UserDetailsEntity;
import com.vijayit.repository.UserDetailsRepository;
import com.vijayit.service.UserService;
import com.vijayit.utilityclasses.EmailSender;
import com.vijayit.utilityclasses.PasswordGenerator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDetailsRepository userRepo;

	@Autowired
	private PasswordGenerator pwdGenerator;

	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private HttpSession session;

	@Override
	public boolean signUp(SignupForm signup) {

		UserDetailsEntity findBystaffEmail = userRepo.findBystaffEmail(signup.getStaffEmail());

		if (findBystaffEmail != null) {
			return false;
		}

		UserDetailsEntity entity = new UserDetailsEntity();

		BeanUtils.copyProperties(signup, entity);

		String generatePassword = pwdGenerator.generatePassword();
		entity.setNewPwd(generatePassword);
		entity.setAccountStatus("LOCKED");

		userRepo.save(entity);

		String staffEmail = entity.getStaffEmail();

		String to = staffEmail;

		String subject = "Office Application";

		String body = "<h1> Please click on below link to unlock your account</h1>"
				+ "<a href=\"http://localhost:8081/unlock?staffEmail="+ to + "\">Unlock your account</a>"
				+ "<p>Your temporary password is:<strong>" + generatePassword + "</string></p>";

		emailSender.sendEmail(subject, body, to);

		return true;
	}

	@Override
	public boolean unlock(UnlockForm unlock) {

		UserDetailsEntity entity = userRepo.findBystaffEmail(unlock.getStaffEmail());

		if (entity.getNewPwd().equals(unlock.getTempPwd())) {

			entity.setNewPwd(unlock.getNewPwd());

			entity.setAccountStatus("UNLOCKED");

			userRepo.save(entity);

			return true;
		}

		return false;
	}

	@Override
	public String login(LoginForm login) {

		UserDetailsEntity entity = userRepo.findBystaffEmailAndNewPwd(login.getStaffEmail(), login.getNewPwd());

		if (entity == null) {

			return "Invalid Credintials";

		} else if (entity.getAccountStatus().equals("LOCKED")) {

			return "Your Account Is In Locked State, Please unlock the account";

		}
		
		session.setAttribute("staffId", entity.getStaffId());

		return "Success";
	}

	@Override
	public boolean forgotPwd(String email) {

		// check record present in DB with that given email
		UserDetailsEntity entity = userRepo.findBystaffEmail(email);

		// if record not available return false
		if (entity == null) {

			return false;
		}
		// if record is present in DB then send the email along with the password
		String subject = "Recovery Password";
		String body = "Your Password ::"+ entity.getNewPwd();
		emailSender.sendEmail(subject, body, email);

		return true;
	}

}
