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
@Table(name = "statuses_tbl")
public class EnquiryStatusEntity {
	
	@Id
	@GeneratedValue
	private Integer statusId;
	private String status;

}
