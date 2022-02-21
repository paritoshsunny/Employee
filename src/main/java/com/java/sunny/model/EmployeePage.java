package com.java.sunny.model;

import java.util.List;

import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePage {
	
	private int pageNumber = 0;
	private int pageSize = 5;
	private Sort.Direction direction = Sort.Direction.ASC;
	private String sortBy = "empCode";
	private List<String> sortedby;
	
	
}
