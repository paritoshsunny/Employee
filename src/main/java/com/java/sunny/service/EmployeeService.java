package com.java.sunny.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.sunny.entity.Employee;
import com.java.sunny.exceptionHandler.BusinessException;
import com.java.sunny.model.EmployeeSearchCriteria;
import com.java.sunny.repo.EmployeeRepo;
import com.java.sunny.specification.EmployeeSpecification;

@Service
public class EmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepo employeeRepo;

//	@Autowired
//	private AddressService addressService;

	// get employee by Id
	public Employee getEmployeeById(int id) {
		logger.trace("Get employee by ID" + id);
		try {
			if (id < 0) {
				throw new BusinessException(LocalDateTime.now().toString(), "601", "Id is less then Zero");
			}
			Employee emp = employeeRepo.findById(id).orElse(null);
			if (emp == null) {
				throw new NullPointerException();
			} else {
				return emp;
			}
		} catch (NumberFormatException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "602",
					"Can not cast parameter to Integer" + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "605", "Id is null" + ex.getMessage());
		} catch (NullPointerException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "606", "Employee not present with id - " + id);
		} catch (Exception ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "603",
					"Something went worng " + ex.getMessage());
		}
	}

	// get list of all employee
	public List<Employee> getAllEmployee() {
		logger.trace("Getting list of all employees");
		try {
			List<Employee> empList = employeeRepo.findAll();
			if (empList.isEmpty()) {
				throw new BusinessException(LocalDateTime.now().toString(), "604",
						"No Employee present in the database ");
			}
		} catch (Exception e) {
			throw new BusinessException(LocalDateTime.now().toString(), "603",
					"Something went worng " + e.getMessage());
		}
		return employeeRepo.findAll();
	}

	// update and save employee
	@Transactional
	public Employee saveEmployee(Employee employee) {
		Employee emp = new Employee();
		try {
			if (employee.equals(null) || employee.getFirstName().isEmpty() || employee.getFirstName().length() == 0) {
				throw new BusinessException(LocalDateTime.now().toString(), "605",
						"Nothing to save or first name is empty, please check.");
			} else if (employee.getSalary() > 999999999) {
				throw new BusinessException(LocalDateTime.now().toString(), "605",
						"Salary must be less then or equal to 999999999");
			}

			if (employee.getEmpCode() != null) {
				emp = employeeRepo.findById(employee.getEmpCode()).orElse(null);
				if (emp == null) {
					throw new NoSuchElementException();
				}
				emp.setFirstName(employee.getFirstName());
				emp.setLastName(employee.getLastName());
				emp.setDesignation(employee.getDesignation());
				emp.setDepartment(employee.getDepartment());
				emp.setModifiedOn(LocalDate.now());
				emp.setEmail(employee.getEmail());
				emp.setSalary(employee.getSalary());
				logger.trace("Updateing existing employee");
				return employeeRepo.save(emp);
			}

			// save an employee
			else {
				emp.setFirstName(employee.getFirstName());
				emp.setLastName(employee.getLastName());
				emp.setDesignation(employee.getDesignation());
				emp.setDepartment(employee.getDepartment());
				emp.setCreatedOn(LocalDate.now());
				emp.setModifiedOn(LocalDate.now());
				emp.setEmail(employee.getEmail());
				emp.setSalary(employee.getSalary());
				emp.setAddId(employee.getAddId());
				logger.trace("Saving an employee");
				return employeeRepo.save(emp);

			}
		} catch (NoSuchElementException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "606",
					"Employee not present by Id " + employee.getEmpCode());
		} catch (IllegalArgumentException e) {
			throw new BusinessException(LocalDateTime.now().toString(), "605",
					"Please provide Employee details" + e.getMessage());
		} catch (Exception ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "603",
					"Something went worng " + ex.getMessage());
		}
	}

	// save list of Employees
	@Transactional
	public List<Employee> saveAllEmployee(List<Employee> employees) {
		logger.trace("Saving list of employees");
		List<Employee> empList = new ArrayList<>();
		try {
			if (employees.isEmpty())
				throw new BusinessException(LocalDateTime.now().toString(), "605", "List is empty, Nothing to save");
			for (Employee emp : employees) {
				Employee employee = new Employee();
				employee.setFirstName(emp.getFirstName());
				employee.setLastName(emp.getLastName());
				employee.setDesignation(emp.getDesignation());
				employee.setDepartment(emp.getDepartment());
				employee.setCreatedOn(LocalDate.now());
				employee.setModifiedOn(LocalDate.now());
				employee.setEmail(emp.getEmail());
				employee.setSalary(emp.getSalary());
				empList.add(employee);
			}
			return employeeRepo.saveAll(empList);
		} catch (Exception e) {
			throw new BusinessException(LocalDateTime.now().toString(), "603",
					"Something went worng " + e.getMessage());
		}
	}

	// get employee by firstName
	public List<Employee> getByFirstName(String fName) {
		logger.trace("Getting list of employee by first name");
		try {
			if (fName.equals(null) || fName.isEmpty()) {
				throw new BusinessException(LocalDateTime.now().toString(), "605", "please provide the FirstName");
			}
		} catch (Exception e) {
			throw new BusinessException(LocalDateTime.now().toString(), "603",
					"Something went worng " + e.getMessage());
		}
		return employeeRepo.getByFirstName(fName);
	}

	// delete employee
	@Transactional
	public String deleteEmployee(int id) {
		logger.trace("deleting employee by Id");
		try {
			if (id < 0) {
				throw new BusinessException(LocalDateTime.now().toString(), "601", "Id is less then Zero");
			}
			Employee emp = employeeRepo.findById(id).orElse(null);
			if (emp != null) {
				employeeRepo.deleteById(id);
				return "Employee removed Successfully";
			} else {
				throw new NullPointerException();
			}
		} catch (NumberFormatException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "602",
					"Can not cast parameter to Integer" + ex.getMessage());
		} catch (NoSuchElementException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "606",
					"Employee not present" + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "605", "Id is null" + ex.getMessage());
		} catch (NullPointerException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "606", "Employee not present with id - " + id);
		} catch (Exception ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "603",
					"Something went worng " + ex.getMessage());
		}

	}

	// Get List of employee in sorted order and filters
	public Page<Employee> getEmployees(Pageable employeePage, EmployeeSearchCriteria employeeSearchCriteria) {
		return (Page<Employee>) employeeRepo
				.findAll(EmployeeSpecification.findAllEmployee(employeeSearchCriteria, employeePage), employeePage);
	}

}
