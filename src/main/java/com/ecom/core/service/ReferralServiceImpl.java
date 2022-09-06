package com.ecom.core.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.core.dto.ReferralIndo;
import com.ecom.core.repository.ReferralRepository;

@Service
public class ReferralServiceImpl implements ReferralService {

	@Autowired
	ReferralRepository repository;

	@Override
	public ReferralIndo saveReferral(ReferralIndo referral) {
		return repository.save(referral);
	}

	@Override
	public ReferralIndo validateReferralCode(String referralCode, Date date) {
		return repository.findByReferralCode(referralCode);
	}

	@Override
	public List<ReferralIndo> getAllReferral() {
		return (List<ReferralIndo>) repository.findAll();
	}

	@Override
	public ReferralIndo findReferralById(Long referralId) {
		ReferralIndo referral = repository.findOne(referralId);

		if (referral != null) {
			if (referral.getIsActive().equalsIgnoreCase("active")) {
				referral.setIsActive("deactive");
			} else {
				referral.setIsActive("active");
			}
			return repository.save(referral);
		}
		return null;
	}

	@Override
	public ReferralIndo findReferralByReferralCode(String referralCode) {
		ReferralIndo referral = repository.findByReferralCodeStatus(referralCode);

		if (referral != null) {
			if (referral.getIsActive().equalsIgnoreCase("active")) {
				referral.setIsActive("deactive");
			} else {
				referral.setIsActive("active");
			}
			return repository.save(referral);
		}
		return null;
	}

	@Override
	public ReferralIndo removeReferralCode(String referralCode) {
		ReferralIndo referral = repository.findByReferralCodeStatus(referralCode);
		if(referral!=null) {
		   repository.delete(referral);
		}
		return referral;
	}

	@Override
	public ReferralIndo updateReferralCode(ReferralIndo request) {
		
		ReferralIndo referral = repository.findByReferralCodeStatus(request.getReferralCode());
		
		if(referral!=null) {
			if(request.getDiscountId()!=null && request.getDiscountId()!=0) {
				referral.setDiscountId(request.getDiscountId());
			}
			if(request.getReferralCode()!=null ) {
				referral.setReferralCode(request.getReferralCode());
			}
			if(request.getUserId()!=null && request.getUserId()!=0) {
				referral.setUserId(request.getUserId());
			}
			if(request.getValiDateTo()!=null) {
				referral.setValiDateTo(request.getValiDateTo());
			}
			if(request.getValidDateFrom()!=null) {
				referral.setValidDateFrom(request.getValidDateFrom());
			}
			if(request.getIsActive()!=null) {
				referral.setIsActive(request.getIsActive());
			}
			
			repository.save(referral);
			return referral;
		}
		
		return null;
	}

}
