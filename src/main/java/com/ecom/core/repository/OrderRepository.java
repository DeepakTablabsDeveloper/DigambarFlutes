package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ecom.core.dto.Orders;

public interface OrderRepository extends PagingAndSortingRepository<Orders, Long> {

	Orders findById(Long id);

	List<Orders> findByCustomerId(Long customerId);

	//@Query(value = "SELECT * FROM Orders /*#pageable*/  ORDER BY date desc",
		//       countQuery = "SELECT count(*) FROM Orders",
		  //     nativeQuery = true)
	@Query(value="select * from orders order by order_date desc",nativeQuery =true)
	List<Orders> findAllorderBydateOrder();

	void deleteById(Long id);
	
	/*
	 * //transaction payment updateOrder()
	 * 
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query(value=
	 * "UPDATE carcheks.transactions a SET a.active=0 where a.id= :id "
	 * ,nativeQuery=true) void updateAllActive(@Param("id") Long id);
	 */
}
