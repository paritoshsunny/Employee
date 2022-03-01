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

import com.java.sunny.entity.Address;
import com.java.sunny.entity.AddressResponse;
import com.java.sunny.service.AddressService;

@RestController
@Validated
@RequestMapping("/add")
public class AddressController {
	
	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private AddressService addressService;

	@PostMapping("/save")
	public ResponseEntity<AddressResponse> saveAddress(@Valid @RequestBody Address add) {
		logger.trace("Inside saveEmployee controller");
		List<Address> addList = new ArrayList<>();
		Address addr = addressService.saveAddress(add);
		addList.add(addr);
		AddressResponse addressResponse = new AddressResponse(addList, "200", "Address saved sucessfully",
				LocalDateTime.now().toString());
		return new ResponseEntity<>(addressResponse, HttpStatus.OK);
	}

	@PostMapping("/saveAll")
	public ResponseEntity<AddressResponse> saveAllAddresses(@Valid @RequestBody List<Address> addrList) {
		logger.trace("Inside saveAllAddresses controller");
		List<Address> addr = addressService.saveAllAddress(addrList);
		AddressResponse addressResponse = new AddressResponse(addr, "200", "All address added successfully",
				LocalDateTime.now().toString());
		return new ResponseEntity<>(addressResponse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<AddressResponse> getAll() {
		logger.trace("Inside getAll controller");
		List<Address> addrList = addressService.getAllAddress();
		AddressResponse addressResponse = new AddressResponse(addrList, "200", "Success",
				LocalDateTime.now().toString());
		return new ResponseEntity<>(addressResponse, HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<AddressResponse> getById(@PathVariable("id") int id) {
		logger.trace("Inside getById controller");
		List<Address> addrList = new ArrayList<>();
		Address add = addressService.getAddressById(id);
		addrList.add(add);
		AddressResponse addressResponse = new AddressResponse(addrList, "200", "Success",
				LocalDateTime.now().toString());
		return new ResponseEntity<>(addressResponse, HttpStatus.OK);
	}

	@GetMapping("/get/{name}")
	public ResponseEntity<AddressResponse> getByName(@PathVariable @NotBlank @Size(min = 2, max = 25) String name) {
		logger.trace("Inside rempveEmployee controller");
		List<Address> addrList = addressService.getByCity(name);
		AddressResponse addressResponse = new AddressResponse(addrList, "200", "Success",
				LocalDateTime.now().toString());
		return new ResponseEntity<>(addressResponse, HttpStatus.OK);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<AddressResponse> rempveEmployee(@PathVariable int id) {
		logger.trace("Inside rempveEmployee controller");
		String status = addressService.deleteAddress(id);
		AddressResponse addressResponse = new AddressResponse(null, "200", status, LocalDateTime.now().toString());
		return new ResponseEntity<>(addressResponse, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<AddressResponse> updateEmployee(@Valid @RequestBody Address address) {
		logger.trace("Inside updateEmployee controller");
		List<Address> addrList = new ArrayList<>();
		Address add = addressService.saveAddress(address);
		addrList.add(add);
		AddressResponse addressResponse = new AddressResponse(addrList, "200", "Employee details updated",
				LocalDateTime.now().toString());
		return new ResponseEntity<>(addressResponse, HttpStatus.OK);
	}

}
