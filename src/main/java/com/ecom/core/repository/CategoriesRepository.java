package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Categories;


@Repository
public interface CategoriesRepository extends PagingAndSortingRepository<Categories, Long> {

	Page<Categories> findById(Long id, Pageable pageable);

	Categories findById(Long id);
	
	void deleteById(Long id);

	List<Categories> findAll();
	
	Page<Categories> findAll(Pageable pageable);

	@Query("from Categories where lower(name) like lower(concat('%', ?1,'%')) ")
	List<Categories> findByNames(String name);

	Categories findByName(String name);

	@Query("from Categories where static_id=3")
	List<Categories> findAllCategories();

	@Query("from Categories where static_id=1")
	List<Categories> findAllBrands();

	List<Categories> findByStaticId(Long staticId);
	
}
