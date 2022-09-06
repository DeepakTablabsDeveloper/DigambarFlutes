package com.ecom.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Banner;

@Repository
public interface BannerRepository extends PagingAndSortingRepository<Banner, Long> {

	void deleteById(Long id);

}
