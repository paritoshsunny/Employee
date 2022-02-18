package com.java.sunny.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.sunny.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	List<Employee> getByFirstName(String fName);

	

}
