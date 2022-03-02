package com.java.sunny.request;

import com.java.sunny.entity.Address;
import com.java.sunny.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveEmployeeRequest {
	
	private Employee employee;
	private Address address;

}
