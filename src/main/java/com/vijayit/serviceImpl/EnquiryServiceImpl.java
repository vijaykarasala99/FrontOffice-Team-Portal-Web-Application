package com.vijayit.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vijayit.bindingclasses.DashboardForm;
import com.vijayit.bindingclasses.EnquiryForm;
import com.vijayit.bindingclasses.EnquirySearchCriteria;
import com.vijayit.entity.CoursesEntity;
import com.vijayit.entity.EnquiryStatusEntity;
import com.vijayit.entity.StudentEntity;
import com.vijayit.entity.UserDetailsEntity;
import com.vijayit.repository.CoursesRepository;
import com.vijayit.repository.EnquiryStatusRepository;
import com.vijayit.repository.StudentRepository;
import com.vijayit.repository.UserDetailsRepository;
import com.vijayit.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private UserDetailsRepository userRepo;

	@Autowired
	private CoursesRepository courseRepo;

	@Autowired
	private EnquiryStatusRepository enqStatusRepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashboardForm getDashboardData(Integer staffId) {

		DashboardForm dashboard = new DashboardForm();

		Optional<UserDetailsEntity> findById = userRepo.findById(staffId);

		if (findById.isPresent()) {

			UserDetailsEntity userDetailsEntity = findById.get();

			List<StudentEntity> studentEnquiries = userDetailsEntity.getStudentEnquiries();

			int totalCount = studentEnquiries.size();

			int enrolledCount = studentEnquiries.stream().filter(e -> e.getStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			int lostCount = studentEnquiries.stream().filter(e -> e.getStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			dashboard.setTotalEnquiries(totalCount);
			dashboard.setEnrolled(enrolledCount);
			dashboard.setLost(lostCount);

		}

		return dashboard;
	}

	@Override
	public List<String> getCourseNames() {

		List<CoursesEntity> findAll = courseRepo.findAll();

		List<String> courseNames = new ArrayList();

		for (CoursesEntity entity : findAll) {
			courseNames.add(entity.getCourseName());
		}

		return courseNames;
	}

	@Override
	public List<String> getEnqStatus() {

		List<EnquiryStatusEntity> findAll = enqStatusRepo.findAll();

		List<String> statusNames = new ArrayList();
		for (EnquiryStatusEntity entity : findAll) {
			statusNames.add(entity.getStatus());
		}

		return statusNames;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {

		StudentEntity entity = new StudentEntity();

		BeanUtils.copyProperties(form, entity);

		Integer staffId = (Integer) session.getAttribute("staffId");

		UserDetailsEntity userDetailsEntity = userRepo.findById(staffId).get();

		entity.setUserDetails(userDetailsEntity);
		

		studentRepo.save(entity);

		return true;
	}

	@Override
	public List<StudentEntity> getStudentDetails() {

		Integer staffId = (Integer) session.getAttribute("staffId");

		Optional<UserDetailsEntity> findById = userRepo.findById(staffId);

		if (findById.isPresent()) {

			UserDetailsEntity detailsEntity = findById.get();

			List<StudentEntity> enquiries = detailsEntity.getStudentEnquiries();

			return enquiries;

		}
		return null;
	}

	@Override
	public List<StudentEntity> getFilteredEnquiries(EnquirySearchCriteria criteria, Integer staffId) {

		Optional<UserDetailsEntity> findById = userRepo.findById(staffId);

		if (findById.isPresent()) {

			UserDetailsEntity detailsEntity = findById.get();

			List<StudentEntity> enquiries = detailsEntity.getStudentEnquiries();

			// here is ajax filter logic

			if (null != criteria.getCourse() & !"".equals(criteria.getCourse())) {

				enquiries = enquiries.stream().filter(e -> e.getCourse().equals(criteria.getCourse()))
						.collect(Collectors.toList());

			}

			if (null != criteria.getStatus() & !"".equals(criteria.getStatus())) {

				enquiries = enquiries.stream().filter(e -> e.getStatus().equals(criteria.getStatus()))
						.collect(Collectors.toList());

			}

			if (null != criteria.getClassMode() & !"".equals(criteria.getClassMode())) {

				enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());

			}

			return enquiries;
		}

		return null;
	}

}
