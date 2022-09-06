package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Cart;

import java.lang.Long;


@Repository
public interface CartRepository extends PagingAndSortingRepository<Cart, Long> {

	Page<Cart> findById(Long id, Pageable pageable);

	Cart findById(Long id);
	
	void deleteById(Long id);

	List<Cart> findAll();
	
	Page<Cart> findAll(Pageable pageable);
	
	Cart findByCustomerIdAndProductId(Long customerid,Long productId);
	
	Cart findByProductId(Long productid);

	void deleteByProductId(Long productId);

	List<Cart> findByCustomerId(Long customerId);

	void deleteByCustomerId(Long customerId);

	void deleteByCustomerIdAndProductId(Long customerId, Long productId);

}
