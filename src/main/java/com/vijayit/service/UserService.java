package com.vijayit.service;

import com.vijayit.bindingclasses.LoginForm;
import com.vijayit.bindingclasses.SignupForm;
import com.vijayit.bindingclasses.UnlockForm;

public interface UserService {
	
	public boolean signUp(SignupForm signup);
	
	public boolean unlock(UnlockForm unlock);
	
	public String login(LoginForm login);
	
	public boolean forgotPwd(String email);

}
