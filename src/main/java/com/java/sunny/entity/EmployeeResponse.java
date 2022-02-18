package com.java.sunny.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

	private List<Employee> employee;
	private String responseCode;
	private String errorMessage;
	private String timeStamp;
	
}
