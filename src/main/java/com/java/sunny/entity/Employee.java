package com.java.sunny.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@Entity
@Table(name="EMPLOYEE_TABLE")
@ToString
public class Employee {
	
	@Id
	@GeneratedValue
	@Min(1)@Max(99999999)
	private Integer empCode;
	
	@NotNull
	@Size(min=2,max=25,message="First name should be mininum 2 char and max 25 char long")
	private String firstName;
	
	@NotNull
	@Size(min=2,max=25,message="First name should be mininum 2 char and max 25 char long")
	private String lastName;
	
	@NotNull
	private String designation;
	
	@NotNull
	private String department;
	private LocalDate createdOn;
	private LocalDate modifiedOn;
	@Email
	@NotNull
	private String email;
	
	
	@Min(10000)@Max(999999999)
	private Integer salary;
	@NotNull
	private Integer addId;
	
}
