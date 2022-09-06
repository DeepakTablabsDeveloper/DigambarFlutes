package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.BaughtProducts;

import java.lang.Long;


@Repository
public interface BaughtPruductsRepository extends PagingAndSortingRepository<BaughtProducts, Long> {

	Page<BaughtProducts> findById(Long id, Pageable pageable);

	BaughtProducts findById(Long id);
	
	void deleteById(Long id);

	List<BaughtProducts> findAll();
	
	Page<BaughtProducts> findAll(Pageable pageable);

	List<BaughtProducts> findByOrderId(Long orderId);

	void deleteByOrderId(Long id);

	Page<BaughtProducts> findByOrderId(Long orderId, Pageable pageable);

}
