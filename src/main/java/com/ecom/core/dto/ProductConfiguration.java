package com.ecom.core.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_configuration") 
//@Table
public class ProductConfiguration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long customerId;
	private Long productId;
	private String handSpecification;
	private String frequency;
	private String guruName;
	private String thread1;
	private String thread2;
	private Date createdOn;
	
	public ProductConfiguration() {
		// TODO Auto-generated constructor stub
	}

	public ProductConfiguration(Long id, Long customerId, Long productId, String handSpecification,
			String frequency,String guruName, String thread1, String thread2, Date createdOn) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.productId = productId;
		this.handSpecification = handSpecification;
		this.frequency = frequency;
		this.guruName = guruName;
		this.thread1 = thread1;
		this.thread2 = thread2;
		this.createdOn = createdOn;
	}

	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "PRODUCT_ID")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Column(name = "HAND_SPECIFICATION")
	public String getHandSpecification() {
		return handSpecification;
	}

	public void setHandSpecification(String handSpecification) {
		this.handSpecification = handSpecification;
	}

	@Column(name = "FREQUENCY")
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	@Column(name = "GURUNAME")
	public String getGuruName() {
		return guruName;
	}

	public void setGuruName(String guruName) {
		this.guruName = guruName;
	}

	@Column(name = "THREAD1")
	public String getThread1() {
		return thread1;
	}

	public void setThread1(String thread1) {
		this.thread1 = thread1;
	}
	@Column(name = "THREAD2")
	public String getThread2() {
		return thread2;
	}

	public void setThread2(String thread2) {
		this.thread2 = thread2;
	}

	@Column(name = "CREATED_ON")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
	

}
