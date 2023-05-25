package com.vijayit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "courses_tbl")
public class CoursesEntity {
	
	@Id
	@GeneratedValue
	private Integer courseId;
	private String courseName;

}
