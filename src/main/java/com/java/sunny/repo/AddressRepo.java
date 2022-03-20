package com.java.sunny.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.sunny.entity.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer>{

	List<Address> getByCity(String cityName);

	Address getAddressByHouseNoAndCity(String houseNo, String city);

}
