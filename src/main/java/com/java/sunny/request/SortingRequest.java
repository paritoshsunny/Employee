package com.java.sunny.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SortingRequest {
	
	List<String> colName;
	List<String> direction;
	int offset;
	int pageSize;

}
