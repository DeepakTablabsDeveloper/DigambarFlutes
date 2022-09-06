package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Customers;


@Repository
public interface CustomersRepository extends PagingAndSortingRepository<Customers, Long> {

	Page<Customers> findById(Long id, Pageable pageable);

	Customers findById(Long id);
	
	void deleteById(Long id);

	@Query("from Customers where is_guest='false' and is_admin='false' ")
	List<Customers> findAll();
	

	List<Customers> findByEmailIdAndPassword(String emailid,String password);
	
	List<Customers> findByMobileNumberAndPassword(String mobileNumber,String password);

	Customers findByEmailId(String emailId);


	Customers findByMobileNumber(String mobileNumber);

	Customers findByMobileNumberAndEmailId(String mobileNumber, String emailId);

	@Query("from Customers where is_guest='true' ")
	List<Customers> findAllGuest();
}
