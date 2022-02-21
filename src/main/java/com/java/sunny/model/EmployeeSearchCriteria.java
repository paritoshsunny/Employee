package com.java.sunny.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EmployeeSearchCriteria {
	
	private String firstName;
	private String lastName;
	private String address;
	private String designation;
	private String department;
	private LocalDate createdOn;
	private LocalDate modifiedOn;
	private String email;
	private int salary;

}
