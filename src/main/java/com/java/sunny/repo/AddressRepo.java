package com.java.sunny.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.sunny.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Integer>{

	List<Address> getByCity(String cityName);

	Address getAddressByHouseNoAndCity(String houseNo, String city);

}
