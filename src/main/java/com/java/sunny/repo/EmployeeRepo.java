package com.java.sunny.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.java.sunny.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>,JpaSpecificationExecutor<Employee> {

	List<Employee> getByFirstName(String fName);
	

}
