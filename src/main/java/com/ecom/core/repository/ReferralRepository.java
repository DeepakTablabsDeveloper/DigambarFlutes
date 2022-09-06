package com.ecom.core.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.ReferralIndo;

@Repository
public interface ReferralRepository extends PagingAndSortingRepository<ReferralIndo, Long> {

	@Query(value = "SELECT * FROM digambarflutes_ecommerce.referral_info WHERE referral_code=? and is_active='active'", nativeQuery = true)
	ReferralIndo findByReferralCode(String referralCode);

	@Query(value = "SELECT * FROM digambarflutes_ecommerce.referral_info WHERE referral_code=?", nativeQuery = true)
	ReferralIndo findByReferralCodeStatus(String referralCode);

}
