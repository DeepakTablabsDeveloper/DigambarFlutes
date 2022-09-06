package com.ecom.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.ProductConfiguration;

@Repository
public interface ProductConfigurationRepository extends PagingAndSortingRepository<ProductConfiguration, Long>  {

		
		@Query(value ="SELECT * FROM product_configuration where customer_id =? LIMIT 1 ", nativeQuery = true)
		ProductConfiguration findByCustomerId(Long customer_id);
		
		}

