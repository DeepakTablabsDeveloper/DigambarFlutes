package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Transactions;

@Repository
public interface TransactionsRepository extends PagingAndSortingRepository<Transactions, Long> {

	List<Transactions> findByUserId(Long customerId);

}
