package com.java.sunny.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.java.sunny.entity.Employee;
import com.java.sunny.model.EmployeeSearchCriteria;

public class EmployeeSpecification {
	
	public static Specification<Employee> findEmployeeByFirstName(String firstName) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
	}
	
	public static void sortEmployee(Pageable p, CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery,
			Root employeeRoot) {
		List<Order> orders= new ArrayList<>();
		if(p.getSort().isSorted()) {
			List<Sort.Order> ol=p.getSort().toList();
			for (Sort.Order t : ol) {
				if(t.getDirection().equals(Sort.Direction.ASC)) {
					criteriaQuery.orderBy(criteriaBuilder.asc(employeeRoot.get(t.getProperty())));
					orders.add(criteriaBuilder.asc(employeeRoot.get(t.getProperty())));
				}else {
					criteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get(t.getProperty())));
					orders.add(criteriaBuilder.desc(employeeRoot.get(t.getProperty())));
				}	
			}
			
		}
		
	}
	
	public static Specification<Employee> findAllEmployee(EmployeeSearchCriteria employeeSearchCriteria,Pageable p){
		return (root, query, criteriaBuilder) ->{
			sortEmployee(p, criteriaBuilder, query, root);
			List<Predicate> predicates = new ArrayList<>();
			if(Objects.nonNull(employeeSearchCriteria.getFirstName()) && employeeSearchCriteria.getFirstName()!="") {
				predicates.add(criteriaBuilder.like(root.get("firstName"), "%"+ employeeSearchCriteria.getFirstName() +"%"));
			}
			if(Objects.nonNull(employeeSearchCriteria.getLastName()) && employeeSearchCriteria.getLastName()!="") {
				predicates.add(criteriaBuilder.like(root.get("lastName"), "%"+ employeeSearchCriteria.getLastName() +"%"));
			}
			if(Objects.nonNull(employeeSearchCriteria.getAddress()) && employeeSearchCriteria.getAddress().getAddId()>=100) {
				predicates.add(criteriaBuilder.like(root.get("addId"), "%"+ employeeSearchCriteria.getAddress() +"%"));
			}
			if(Objects.nonNull(employeeSearchCriteria.getAddress()) && employeeSearchCriteria.getAddress().getHouseNo()!="") {
				predicates.add(criteriaBuilder.like(root.get("houseNo"), "%"+ employeeSearchCriteria.getAddress() +"%"));
			}
			if(Objects.nonNull(employeeSearchCriteria.getAddress()) && employeeSearchCriteria.getAddress().getCity()!="") {
				predicates.add(criteriaBuilder.like(root.get("city"), "%"+ employeeSearchCriteria.getAddress() +"%"));
			}
			if(Objects.nonNull(employeeSearchCriteria.getDesignation()) && employeeSearchCriteria.getDesignation()!="") {
				predicates.add(criteriaBuilder.like(root.get("designation"), "%"+ employeeSearchCriteria.getDesignation() +"%"));
			}
			if(Objects.nonNull(employeeSearchCriteria.getDepartment()) && employeeSearchCriteria.getDepartment()!="") {
				predicates.add(criteriaBuilder.like(root.get("department"), "%"+ employeeSearchCriteria.getDepartment() +"%"));
			}
			if(Objects.nonNull(employeeSearchCriteria.getCreatedOn()) && employeeSearchCriteria.getCreatedOn().toString()!="") {
				predicates.add(criteriaBuilder.like(root.get("createdOn"), "%"+ employeeSearchCriteria.getCreatedOn() +"%"));
			}
			if(Objects.nonNull(employeeSearchCriteria.getModifiedOn()) && employeeSearchCriteria.getModifiedOn().toString()!="") {
				predicates.add(criteriaBuilder.like(root.get("modifiedOn"), "%"+ employeeSearchCriteria.getModifiedOn() +"%"));
			}
			if(Objects.nonNull(employeeSearchCriteria.getEmail()) && employeeSearchCriteria.getEmail()!="") {
				predicates.add(criteriaBuilder.like(root.get("email"), "%"+ employeeSearchCriteria.getEmail() +"%"));
			}
			if(employeeSearchCriteria.getSalary()!=0) {
				predicates.add(criteriaBuilder.like(root.get("salary"), "%"+ employeeSearchCriteria.getSalary() +"%"));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		
	}

}
