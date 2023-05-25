 package com.vijayit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "userdetails_tbl")
public class UserDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer staffId;

	@Column(name = "Name")
	private String staffName;

	@Column(name = "Email", unique = true)
	private String staffEmail;

	@Column(name = "PhoneNumber")
	private Long staffPhoneNum;

	@Column(name = "Password")
	private String newPwd;

	@Column(name = "AccountStatus")
	private String accountStatus;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userDetails")
	private List<StudentEntity> studentEnquiries;

}
