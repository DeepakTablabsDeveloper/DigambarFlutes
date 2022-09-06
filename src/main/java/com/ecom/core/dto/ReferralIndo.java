package com.ecom.core.dto;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "referral_info")
public class ReferralIndo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "referralCode")
	private String referralCode;
	@Column(name = "validDateFrom")
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date validDateFrom;
	@Column(name = "valiDateTo")
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date valiDateTo;
	@Column(name = "userId")
	private Long userId;
	@Column(name = "discountId")
	private Long discountId;
	@Column(name = "isActive")
	private String isActive;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReferralCode() {
		return referralCode;
	}
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}
	public Date getValidDateFrom() {
		return validDateFrom;
	}
	public void setValidDateFrom(Date validDateFrom) {
		this.validDateFrom = validDateFrom;
	}
	public Date getValiDateTo() {
		return valiDateTo;
	}
	public void setValiDateTo(Date valiDateTo) {
		this.valiDateTo = valiDateTo;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public ReferralIndo(Long id, String referralCode, Date validDateFrom, Date valiDateTo, Long userId, Long discountId,
			String isActive) {
		super();
		this.id = id;
		this.referralCode = referralCode;
		this.validDateFrom = validDateFrom;
		this.valiDateTo = valiDateTo;
		this.userId = userId;
		this.discountId = discountId;
		this.isActive = isActive;
	}
	public ReferralIndo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
