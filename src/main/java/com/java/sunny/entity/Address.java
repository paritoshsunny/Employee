package com.java.sunny.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="ADDRESS")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Max(99999)
	private Integer addId;
	@NotNull
	@Size(min=1,max=5,message="House number should be mininum 1 char and max 5 char long")
	private String houseNo;
	@NotNull
	@Size(min=2,max=25,message="City should be mininum 2 char and max 25 char long")
	private String city;
	
	@ManyToMany(mappedBy = "addresses")
	private List<Employee> employees;
	

}
