package com.java.sunny.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.java.sunny.entity.Employee;
import com.java.sunny.model.EmployeePage;
import com.java.sunny.model.EmployeeSearchCriteria;

@Repository
public class EmployeeCriteriaRepository {

	private final EntityManager entityManager;
	private final CriteriaBuilder criteriaBuilder;

	public EmployeeCriteriaRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	public Page<Employee> findAllWithfilters(EmployeePage employeePage, EmployeeSearchCriteria employeeSearchCriteria) {

		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
		Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
		Predicate predicate = getPredicate(employeeSearchCriteria, employeeRoot);
		criteriaQuery.where(predicate);
		setOrder(employeePage, criteriaQuery,employeeRoot);
		
		TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(employeePage.getPageNumber() * employeePage.getPageSize());
		typedQuery.setMaxResults(employeePage.getPageSize());
		
		Pageable pageable = getPageable(employeePage);
		long employeeCount = getEmployeeCount(predicate);
		
		return new PageImpl<Employee>(typedQuery.getResultList(), pageable, employeeCount);

	}

	private Predicate getPredicate(EmployeeSearchCriteria employeeSearchCriteria, Root<Employee> employeeRoot) {

		List<Predicate> predicates = new ArrayList<>();
		if(Objects.nonNull(employeeSearchCriteria.getFirstName())) {
			predicates.add(criteriaBuilder.like(employeeRoot.get("firstName"), "%"+ employeeSearchCriteria.getFirstName() +"%"));
		}
		if(Objects.nonNull(employeeSearchCriteria.getLastName())) {
			predicates.add(criteriaBuilder.like(employeeRoot.get("lastName"), "%"+ employeeSearchCriteria.getLastName() +"%"));
		}
		if(Objects.nonNull(employeeSearchCriteria.getAddress())) {
			predicates.add(criteriaBuilder.like(employeeRoot.get("address"), "%"+ employeeSearchCriteria.getAddress() +"%"));
		}
		if(Objects.nonNull(employeeSearchCriteria.getDesignation())) {
			predicates.add(criteriaBuilder.like(employeeRoot.get("designation"), "%"+ employeeSearchCriteria.getDesignation() +"%"));
		}
		if(Objects.nonNull(employeeSearchCriteria.getDepartment())) {
			predicates.add(criteriaBuilder.like(employeeRoot.get("department"), "%"+ employeeSearchCriteria.getDepartment() +"%"));
		}
		if(Objects.nonNull(employeeSearchCriteria.getCreatedOn())) {
			predicates.add(criteriaBuilder.like(employeeRoot.get("createdOn"), "%"+ employeeSearchCriteria.getCreatedOn() +"%"));
		}
		if(Objects.nonNull(employeeSearchCriteria.getModifiedOn())) {
			predicates.add(criteriaBuilder.like(employeeRoot.get("modifiedOn"), "%"+ employeeSearchCriteria.getModifiedOn() +"%"));
		}
		if(Objects.nonNull(employeeSearchCriteria.getEmail())) {
			predicates.add(criteriaBuilder.like(employeeRoot.get("email"), "%"+ employeeSearchCriteria.getEmail() +"%"));
		}
		if(employeeSearchCriteria.getSalary()!=0) {
			predicates.add(criteriaBuilder.like(employeeRoot.get("salary"), "%"+ employeeSearchCriteria.getSalary() +"%"));
		}
		
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
	
	private void setOrder(EmployeePage employeePage, CriteriaQuery<Employee> criteriaQuery,
			Root<Employee> employeeRoot) {

		if(employeePage.getDirection().equals(Sort.Direction.ASC)) {
			criteriaQuery.orderBy(criteriaBuilder.asc(employeeRoot.get(employeePage.getSortBy())));
		}else {
			criteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get(employeePage.getSortBy())));
		}
	}
	


	private Pageable getPageable(EmployeePage employeePage) {
		Sort sort = Sort.by(employeePage.getDirection(),employeePage.getSortBy());
		return (Pageable) PageRequest.of(employeePage.getPageNumber(), employeePage.getPageSize(), sort);
	}

	private long getEmployeeCount(Predicate predicate) {
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Employee> countRoot = countQuery.from(Employee.class);
		countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
		return entityManager.createQuery(countQuery).getSingleResult();
	}

}
