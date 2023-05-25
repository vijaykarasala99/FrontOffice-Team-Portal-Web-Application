package com.vijayit.bindingclasses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnquiryForm {

	private Integer enquiryId;
	private String studentName;
	private Long contactNumber;
	private String classMode;
	private String course;
	private String status;

}
