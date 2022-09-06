package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Products;


@Repository
public interface ProductsRepository extends PagingAndSortingRepository<Products, Long> {

	Page<Products> findById(Long id, Pageable pageable);

	@Query(value = "select * from products where id =? limit 1",nativeQuery=true)
	Products findById(Long id);
	
	void deleteById(Long id);

	List<Products> findAll();
	
	Page<Products> findAll(Pageable pageable);

	List<Products> findBySubCategoryId(Long subCategoryId);

	@Query("from Products where lower(name) like lower(concat('%', ?1,'%'))")
	List<Products> findByNames(String name);


	List<Products> findByOrderByNoOfPieceSoldDesc();

	@Query(value="select * from products where cost between ?1 and ?2 and sub_category_id =?3",nativeQuery=true)
	List<Products> findByCostAndSubCategoryId(Long minPrice, Long maxPrice, Long subCategoryId);

	@Query(value="select * from products order by id desc limit 5",nativeQuery=true)
	List<Products> findNewProducts();

	List<Products> findByBrandId(Long brandId);

	@Query(value="select * from products where cost between ?1 and ?2 ",nativeQuery=true)
	List<Products> findByCost(Long minPrice, Long maxPrice);

	List<Products> findBySubCategoryIdAndBrandId(Long subCategoryId, Long brandId);

	@Query(value="select * from products where cost between ?1 and ?2 and brand_id =?3",nativeQuery=true)
	List<Products> findByCostAndBrandId(Long minPrice, Long maxPrice, Long brandId);
	
	@Query(value="select * from products where cost between ?1 and ?2 and sub_category_id =?3 and brand_id =?4",nativeQuery=true)
	List<Products> findByCostAndSubCategoryIdAndBrandId(Long minPrice, Long maxPrice, Long subCategoryId, Long brandId);
	
	@Query(value="select * from products where (name) like (concat('%', ?1,'%'))",nativeQuery=true)
	List<Products> findByKeyWord(String keyword);
	
}
