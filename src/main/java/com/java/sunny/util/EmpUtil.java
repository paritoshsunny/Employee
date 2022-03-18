package com.java.sunny.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.java.sunny.VO.AddressVO;
import com.java.sunny.VO.EmployeeVO;
import com.java.sunny.entity.Address;
import com.java.sunny.entity.Employee;

public class EmpUtil {
	
	public static List<EmployeeVO> convertEmpEntityToVO(List<Employee> employees) {
		List<EmployeeVO> employeeVOs= new ArrayList<>();
		if(employees!=null) {
			for (Employee employee : employees) {
				EmployeeVO employeeVO = new EmployeeVO();
				employeeVO.setEmpCode(employee.getEmpCode().toString());
				employeeVO.setFirstName(employee.getFirstName());
				employeeVO.setLastName(employee.getLastName());
				employeeVO.setDesignation(employee.getDesignation());
				employeeVO.setDepartment(employee.getDepartment());
				employeeVO.setCreatedOn(employee.getCreatedOn().toString());
				employeeVO.setModifiedOn(employee.getModifiedOn().toString());
				employeeVO.setEmail(employee.getEmail());
				employeeVO.setSalary(employee.getSalary()+"");
				List<AddressVO> addressVoList=new ArrayList<>();
				
				for (Address address : employee.getAddresses()) {
					AddressVO addressVO=new AddressVO();
					addressVO.setAddId(address.getAddId().toString());
					addressVO.setHouseNo(address.getHouseNo());
					addressVO.setCity(address.getCity());
					addressVoList.add(addressVO);
				}
				employeeVO.setAddressVo(addressVoList);
				employeeVOs.add(employeeVO);
			}
		}
		return employeeVOs;
	}
	

}
