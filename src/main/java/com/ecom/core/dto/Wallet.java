package com.ecom.core.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
/*  @Table(name="wallet",catalog = "ecommerce") */
@Table
public class Wallet {
	
	private Long id;
	private Long customerId;
	private BigDecimal amount;
	
	
	 public Wallet() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 
	 
	public Wallet(Long customerId, BigDecimal amount) {
		super();
	
		this.customerId = customerId;
		this.amount = amount;
	}



	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="ID", unique=true, nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	@Column(name="AMOUNT")
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
}
