package com.java.sunny.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.sunny.entity.Address;
import com.java.sunny.entity.Employee;
import com.java.sunny.entity.EmployeeResponse;
import com.java.sunny.exceptionHandler.BusinessException;
import com.java.sunny.request.SaveEmployeeRequest;

@Service
public class SaveEmployeeService {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	AddressService addressService;
	
	@Transactional
	public EmployeeResponse saveEmployeeWithAddress(SaveEmployeeRequest saveEmployeeRequest) {
		EmployeeResponse employeeResponse;
		Employee employee = saveEmployeeRequest.getEmployee();
		Address address = saveEmployeeRequest.getAddress();
		Employee e =new Employee();
		Address add=addressService.getAddressByHouseNoAndCity(address.getHouseNo(), address.getCity());
		if(add==null) {
			add=addressService.saveAddress(address);
			employee.setAddId(add.getAddId().intValue());
			e=employeeService.saveEmployee(employee);
			List<Employee> emplist=new ArrayList<>();
			emplist.add(e);
			 employeeResponse=new EmployeeResponse(emplist, "200", "Success", LocalDateTime.now().toString());
		}else {
			throw new BusinessException(LocalDateTime.now().toString(), "666", "Address already present");
		}
		
		
			return employeeResponse;
		
		
	}
	

}
