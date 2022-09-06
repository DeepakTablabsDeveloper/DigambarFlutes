package com.ecom.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Wallet;

@Repository
public interface WalletReposiory  extends PagingAndSortingRepository<Wallet, Long>
{

	Wallet findByCustomerId(Long customerId);

}
