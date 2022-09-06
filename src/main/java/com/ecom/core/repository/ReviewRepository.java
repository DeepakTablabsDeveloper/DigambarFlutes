package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Reviews;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Reviews, Long> {

	Reviews findByUserIdAndProductId(Long userId, Long productId);

	List<Reviews> findByProductId(Long productId);

}
