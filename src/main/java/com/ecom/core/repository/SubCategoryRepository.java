package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.SubCategory;


@Repository
public interface SubCategoryRepository extends PagingAndSortingRepository<SubCategory, Long> {



	SubCategory findById(Long id);
	
	void deleteById(Long id);

	List<SubCategory> findAll();
	
	Page<SubCategory> findAll(Pageable pageable);

	List<SubCategory> findByCategoryId(Long categoryId);

	@Query("from SubCategory where lower(name) like lower(concat('%', ?1,'%'))")
	List<SubCategory> findByNames(String name);
	
}
