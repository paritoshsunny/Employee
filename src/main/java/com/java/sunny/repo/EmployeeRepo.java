package com.java.sunny.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.java.sunny.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>,JpaSpecificationExecutor<Employee> {

	List<Employee> getByFirstName(String fName);
	

}
