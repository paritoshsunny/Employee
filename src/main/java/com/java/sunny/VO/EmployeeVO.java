package com.java.sunny.VO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeVO {
	
	private String empCode;
	private String firstName;
	private String lastName;
	private String designation;
	private String department;
	private String createdOn;
	private String modifiedOn;
	private String email;
	private String salary;
	private List<AddressVO> addressVo;

}
