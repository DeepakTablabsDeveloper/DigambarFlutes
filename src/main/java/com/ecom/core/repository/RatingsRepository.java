package com.ecom.core.repository;
/*
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Ratings;

@Repository
public interface RatingsRepository extends PagingAndSortingRepository<Ratings, Long> {

	

	Ratings findByProductIdAndCustomerId(Long productId, Long cusomerId);

	List<Ratings> findByProductId(Long id);

	void deleteByProductIdAndCustomerId(Long producId, Long customerId);

	@Query("from Ratings  where product_id=?1 and comments is NOT NULL")
	List<Ratings> findByProductIdAndComment(Long productId);

}
*/