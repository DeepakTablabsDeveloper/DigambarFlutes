package com.ecom.core.service;

import java.sql.Date;
import java.util.List;

import com.ecom.core.dto.ReferralIndo;

public interface ReferralService {

	ReferralIndo saveReferral(ReferralIndo referral);

	ReferralIndo validateReferralCode(String referralCode, Date date);

	List<ReferralIndo> getAllReferral();

	ReferralIndo findReferralById(Long referralId);

	ReferralIndo findReferralByReferralCode(String referralCode);

	ReferralIndo removeReferralCode(String referralCode);

	ReferralIndo updateReferralCode(ReferralIndo referralRequest);

}
