package com.vijayit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vijayit.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
	
	
	
	//@Query("select count(*) from StudentEntity where id :staffId")
	//public int totalEnquiries();
	
	//@Query("select count(*) from StudentEntity where status = 'enrolled' and id = ")
	//public int enrolledStudentCount();
	
	//@Query("select count(*) from StudentEntity where status = 'lost'")
	//public int lostStudentCount();
		
	
	//@Query(value = "select count(*) from StudentEntity where staffId = :id and status = 'enrolled'",nativeQuery = true)
	//void selectStatus(@Param("id") Integer id,);

	

}
