package com.ecom.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.ProductType;

@Repository
public interface ProductTypeRepository extends PagingAndSortingRepository<ProductType, Long>  {

	ProductType findById(Long id);
	}
