package com.vijayit.utilityclasses;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {
	
	private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	private static final String NUMBER = "0123456789";
	//private static final String SPECIAL_CHAR = "!@#$%^&*()_-+=<>?/{}[]|";
	private static final String ALL_CHAR =CHAR_LOWER + CHAR_UPPER+  NUMBER ;
	//SecureRandom is a subclass of the Random
	private static final SecureRandom RANDOM = new SecureRandom();
	

	public  String generatePassword() {
		StringBuilder password = new StringBuilder();
		int length = 6;
		for (int i = 0; i < length; i++) {
			//nextInt() method is used to generate a random integer within the specified range (min and max),
			//nextInt() is randomly select only some integer value
			int randomIndex = RANDOM.nextInt(ALL_CHAR.length());
			//based on the integer or index value chatAt() method will pick the value
			password.append(ALL_CHAR.charAt(randomIndex));
		}
		return password.toString();
	}

}
