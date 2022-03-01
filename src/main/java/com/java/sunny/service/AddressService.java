package com.java.sunny.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.sunny.entity.Address;
import com.java.sunny.exceptionHandler.BusinessException;
import com.java.sunny.repo.AddressRepo;

@Service
public class AddressService {

	Logger logger = LoggerFactory.getLogger(AddressService.class);
	
	@Autowired
	AddressRepo addressRepo;
	
	// get address by id
	public Address getAddressById(int id) {
		logger.trace("Get Address by ID" + id);
		try {
			if (id < 0) {
				throw new BusinessException(LocalDateTime.now().toString(), "601", "Id is less then Zero");
			}
			Address address = addressRepo.findById(id).orElse(null);
			if(address==null) {
				throw new NullPointerException();
			}else {
				return address;
			}
		} catch (NumberFormatException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "602",
					"Can not cast parameter to Integer" + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "605", "Id is null" + ex.getMessage());
		} catch (NullPointerException ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "606", "Address not present with id - " + id);
		} catch (Exception ex) {
			throw new BusinessException(LocalDateTime.now().toString(), "603",
					"Something went worng " + ex.getMessage());
		}
	}
	
	// get list of all address
		public List<Address> getAllAddress() {
			logger.trace("Getting list of all addresses");
			try {
				List<Address> addList = addressRepo.findAll();
				if (addList.isEmpty()) {
					throw new BusinessException(LocalDateTime.now().toString(), "604",
							"No address present in the database ");
				}
			} catch (Exception e) {
				throw new BusinessException(LocalDateTime.now().toString(), "603",
						"Something went worng " + e.getMessage());
			}
			return addressRepo.findAll();
		}
		
		// update and save address
			public Address saveAddress(Address add) {
				try {
					if(add.equals(null)||add.getCity().isEmpty()||add.getCity().length()==0) {
						throw new BusinessException(LocalDateTime.now().toString(), "605", "Nothing to save or city name is empty, please check.");
					}
					Address existingadd;

					if (add.getAddId()>=100 || add.getAddId()<=99999) {
						existingadd = addressRepo.findById(add.getAddId()).orElse(null);
						if (existingadd==null) {
							throw new NoSuchElementException();
						}
						existingadd.setCity(add.getCity());
						existingadd.setHouseNo(add.getHouseNo());
						
						logger.trace("Updateing existing address");
						return addressRepo.save(existingadd);
					}

					// save an address
					else {
						logger.trace("Saving an address");
						return addressRepo.save(add);

					}
				} catch (NoSuchElementException ex) {
					throw new BusinessException(LocalDateTime.now().toString(), "606", "Address not present by Id " + add.getAddId());
				} catch (IllegalArgumentException e) {
					throw new BusinessException(LocalDateTime.now().toString(), "605", "Please provide address details" + e.getMessage());
				} catch (Exception ex) {
					throw new BusinessException(LocalDateTime.now().toString(), "603", "Something went worng " + ex.getMessage());
				}
			}

		// save list of address
		public List<Address> saveAllAddress(List<Address> addresses) {
			logger.trace("Saving list of addresses");
			try {
				if (addresses.isEmpty())
					throw new BusinessException(LocalDateTime.now().toString(), "605", "List is empty, Nothing to save");
				return addressRepo.saveAll(addresses);
			} catch (Exception e) {
				throw new BusinessException(LocalDateTime.now().toString(), "603",
						"Something went worng " + e.getMessage());
			}
		}

		// get address by city Name
		public List<Address> getByCity(String cityName) {
			logger.trace("Getting list of address by city name");
			try {
				if (cityName.equals(null) || cityName.isEmpty()) {
					throw new BusinessException(LocalDateTime.now().toString(), "605", "please provide the City name");
				}
			} catch (Exception e) {
				throw new BusinessException(LocalDateTime.now().toString(), "603",
						"Something went worng " + e.getMessage());
			}
			return addressRepo.getByCity(cityName);
		}

		// delete address
		public String deleteAddress(int id) {
			logger.trace("deleting Address by Id");
			try {
				if (id < 0) {
					throw new BusinessException(LocalDateTime.now().toString(), "601", "Id is less then Zero");
				}
				Address address = addressRepo.findById(id).orElse(null);
				if (address != null) {
					addressRepo.deleteById(id);
					return "Address removed Successfully";
				} else {
					throw new NullPointerException();
				}
			} catch (NumberFormatException ex) {
				throw new BusinessException(LocalDateTime.now().toString(), "602",
						"Can not cast parameter to Integer" + ex.getMessage());
			} catch (NoSuchElementException ex) {
				throw new BusinessException(LocalDateTime.now().toString(), "606", "Address not present" + ex.getMessage());
			}catch (IllegalArgumentException ex) {
				throw new BusinessException(LocalDateTime.now().toString(), "605", "Id is null" + ex.getMessage());
			} catch (NullPointerException ex) {
				throw new BusinessException(LocalDateTime.now().toString(), "606", "Address not present with id - " + id);
			} catch (Exception ex) {
				throw new BusinessException(LocalDateTime.now().toString(), "603",
						"Something went worng " + ex.getMessage());
			}

		}
		
		public Address getAddressByHouseNoAndCity(String houseNo,String city){
			Address add=addressRepo.getAddressByHouseNoAndCity(houseNo, city);
			return add;
			
		}
	
}
