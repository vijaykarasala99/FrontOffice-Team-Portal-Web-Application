package com.vijayit.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.vijayit.entity.CoursesEntity;
import com.vijayit.entity.EnquiryStatusEntity;
import com.vijayit.repository.CoursesRepository;
import com.vijayit.repository.EnquiryStatusRepository;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CoursesRepository courseRepo;

	@Autowired
	private EnquiryStatusRepository enqStatusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		courseRepo.deleteAll();
		
		CoursesEntity c1 = new CoursesEntity();
		c1.setCourseName("Java");
		
		CoursesEntity c2 = new CoursesEntity();
		c2.setCourseName("DevOps");
		
		CoursesEntity c3 = new CoursesEntity();
		c3.setCourseName("SpringBoot");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		enqStatusRepo.deleteAll();
		
		EnquiryStatusEntity e1 = new EnquiryStatusEntity();
		e1.setStatus("New");
		
		EnquiryStatusEntity e2 = new EnquiryStatusEntity();
		e2.setStatus("Enrolled");
		
		EnquiryStatusEntity e3 = new EnquiryStatusEntity();
		e3.setStatus("Lost");
		
		enqStatusRepo.saveAll(Arrays.asList(e1,e2,e3));
	}

}
