package com.java.sunny.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.sunny.entity.Employee;
import com.java.sunny.entity.EmployeeResponse;
import com.java.sunny.model.EmployeeSearchCriteria;
import com.java.sunny.request.SaveEmployeeRequest;
import com.java.sunny.service.EmployeeService;
import com.java.sunny.service.SaveEmployeeService;

@RestController
@Validated
@RequestMapping("/emp")
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private SaveEmployeeService saveEmployeeService;

	@PostMapping("/save")
	public ResponseEntity<EmployeeResponse> saveEmployee(@Valid @RequestBody Employee emp) {
		logger.trace("Inside saveEmployee controller");
		List<Employee> empList = new ArrayList<>();
		Employee empl = employeeService.saveEmployee(emp);
		empList.add(empl);
		EmployeeResponse employeeResponse = new EmployeeResponse(empList, "200", "Employee saved sucessfully",
				LocalDateTime.now().toString());
		return new ResponseEntity<EmployeeResponse>(employeeResponse, HttpStatus.OK);
	}

	@PostMapping("/saveAll")
	public ResponseEntity<EmployeeResponse> saveAllEmployee(@Valid @RequestBody List<Employee> emp) {
		logger.trace("Inside saveAllEmployee controller");
		List<Employee> empList = employeeService.saveAllEmployee(emp);
		EmployeeResponse employeeResponse = new EmployeeResponse(empList, "200", "All employees added successfully",
				LocalDateTime.now().toString());
		return new ResponseEntity<EmployeeResponse>(employeeResponse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<EmployeeResponse> getAll() {
		logger.trace("Inside getAll controller");
		List<Employee> empList = employeeService.getAllEmployee();
		EmployeeResponse employeeResponse = new EmployeeResponse(empList, "200", "Success",
				LocalDateTime.now().toString());
		return new ResponseEntity<EmployeeResponse>(employeeResponse, HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<EmployeeResponse> getById(@PathVariable("id") int id) {
		logger.trace("Inside getById controller");
		List<Employee> empList = new ArrayList<>();
		Employee empl = employeeService.getEmployeeById(id);
		empList.add(empl);
		EmployeeResponse employeeResponse = new EmployeeResponse(empList, "200", "Success",
				LocalDateTime.now().toString());
		return new ResponseEntity<EmployeeResponse>(employeeResponse, HttpStatus.OK);
	}

	@GetMapping("/get/{name}")
	public ResponseEntity<EmployeeResponse> getByName(@PathVariable @NotBlank @Size(min = 2, max = 25) String name) {
		logger.trace("Inside rempveEmployee controller");
		List<Employee> empList = employeeService.getByFirstName(name);
		EmployeeResponse employeeResponse = new EmployeeResponse(empList, "200", "Success",
				LocalDateTime.now().toString());
		return new ResponseEntity<EmployeeResponse>(employeeResponse, HttpStatus.OK);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<EmployeeResponse> rempveEmployee(@PathVariable int id) {
		logger.trace("Inside rempveEmployee controller");
		String status = employeeService.deleteEmployee(id);
		EmployeeResponse employeeResponse = new EmployeeResponse(null, "200", status, LocalDateTime.now().toString());
		return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<EmployeeResponse> updateEmployee(@Valid @RequestBody Employee emp) {
		logger.trace("Inside updateEmployee controller");
		List<Employee> empList = new ArrayList<>();
		Employee empl = employeeService.saveEmployee(emp);
		empList.add(empl);
		EmployeeResponse employeeResponse = new EmployeeResponse(empList, "200", "Employee details updated",
				LocalDateTime.now().toString());
		return new ResponseEntity<EmployeeResponse>(employeeResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getemp")
	public ResponseEntity<Page<Employee>> getEmployees(Pageable employeePage, EmployeeSearchCriteria employeeSearchCriteria){
		return new ResponseEntity<>(employeeService.getEmployees(employeePage, employeeSearchCriteria),HttpStatus.OK);
	}
	
	@PostMapping("/saveEmployeeWithAddress")
	public ResponseEntity<EmployeeResponse> saveEmployeeWithAddress(@RequestBody SaveEmployeeRequest saveEmployeeRequest){
		logger.trace("Inside saveEmployeeWithAddress controller");
		EmployeeResponse status = saveEmployeeService.saveEmployeeWithAddress(saveEmployeeRequest);
		return new ResponseEntity<>(status, HttpStatus.OK);
		
	}
}
