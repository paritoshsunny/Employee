package com.java.sunny.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.sunny.entity.Employee;
import com.java.sunny.exceptionHandler.BusinessException;
import com.java.sunny.repo.EmployeeRepo;

@Service
public class EmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepo employeeRepo;

	// get employee by Id
	public Employee getEmployeeById(int id) {
		logger.trace("Get employee by ID" + id);
		try {
			if (id < 0) {
				throw new BusinessException(LocalDateTime.now().toString(), "601", "Id is less then Zero");
			}
			Employee emp = employeeRepo.findById(id).orElse(null);
			if(emp==null) {
				throw new NullPointerException();
			}else {
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

	// save list of Employees
	public List<Employee> saveAllEmployee(List<Employee> employees) {
		logger.trace("Saving list of employees");
		try {
			if (employees.isEmpty())
				throw new BusinessException(LocalDateTime.now().toString(), "605", "List is empty, Nothing to save");
			for (Employee emp : employees) {
				emp.setCreatedOn(LocalDate.now());
				emp.setModifiedOn(LocalDate.now());
			}
			return employeeRepo.saveAll(employees);
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
			throw new BusinessException(LocalDateTime.now().toString(), "606", "Employee not present" + ex.getMessage());
		}catch (IllegalArgumentException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "605", "Id is null" + ex.getMessage());
		} catch (NullPointerException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "606", "Employee not present with id - " + id);
		} catch (Exception ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "603",
					"Something went worng " + ex.getMessage());
		}

	}

	// update and save employee
	public Employee saveEmployee(Employee employee) {
		try {
			if(employee.equals(null)||employee.getFirstName().isEmpty()||employee.getFirstName().length()==0) {
				throw new BusinessException(LocalDateTime.now().toString(), "605", "Nothing to save or first name is empty, please check.");
			}else if (employee.getSalary()>999999999) {
				throw new BusinessException(LocalDateTime.now().toString(), "605", "Salary must be less then or equal to 999999999");
			}
			Employee existingEmp;

			if (employee.getEmpCode() != null) {
				existingEmp = employeeRepo.findById(employee.getEmpCode()).orElse(null);
				if (existingEmp==null) {
					throw new NoSuchElementException();
				}
				existingEmp.setFirstName(employee.getFirstName());
				existingEmp.setLastName(employee.getLastName());
				existingEmp.setAddress(employee.getAddress());
				existingEmp.setDesignation(employee.getDesignation());
				existingEmp.setDepartment(employee.getDepartment());
				existingEmp.setModifiedOn(LocalDate.now());
				existingEmp.setEmail(employee.getEmail());
				existingEmp.setSalary(employee.getSalary());
				logger.trace("Updateing existing employee");
				return employeeRepo.save(existingEmp);
			}

			// save an employee
			else {
				employee.setCreatedOn(LocalDate.now());
				employee.setModifiedOn(LocalDate.now());
				logger.trace("Saving an employee");
				return employeeRepo.save(employee);

			}
		} catch (NoSuchElementException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "606", "Employee not present by Id " + employee.getEmpCode());
		} catch (IllegalArgumentException e) {
			throw new BusinessException(LocalDateTime.now().toString(), "605", "Please provide Employee details" + e.getMessage());
		} catch (Exception ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "603",
					"Something went worng " + ex.getMessage());
		}
		
	}
}
