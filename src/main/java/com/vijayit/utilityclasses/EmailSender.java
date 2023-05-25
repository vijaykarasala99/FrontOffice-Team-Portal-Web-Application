package com.vijayit.utilityclasses;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	
	@Autowired
	//predefined class in spring boot
	private JavaMailSender javaMailSender;
	
	public boolean sendEmail(String subject, String body, String to) {
		
		try {
			
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
			
			messageHelper.setSubject(subject);
			messageHelper.setText(body, true);
			messageHelper.setTo(to);
			//messageHelper.addAttachment("plans", file);
			//messageHelper.addAttachment("plans.xls", file);
			//messageHelper.addAttachment("plans.pdf", file);
			
			javaMailSender.send(mimeMessage);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}


}
