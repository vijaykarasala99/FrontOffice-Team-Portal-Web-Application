package com.vijayit.service;

import java.util.List;

import com.vijayit.bindingclasses.DashboardForm;
import com.vijayit.bindingclasses.EnquiryForm;
import com.vijayit.bindingclasses.EnquirySearchCriteria;
import com.vijayit.entity.StudentEntity;

public interface EnquiryService {
	
	public DashboardForm getDashboardData(Integer staffId);
	
	public boolean saveEnquiry(EnquiryForm form);

	public List<String> getCourseNames();
	
	public List<String> getEnqStatus();
	
	public List<StudentEntity> getStudentDetails();
	
	public List<StudentEntity> getFilteredEnquiries(EnquirySearchCriteria criteria, Integer StaffId);
}
