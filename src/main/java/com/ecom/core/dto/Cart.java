package com.ecom.core.dto;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


	@Entity
	/* @Table(name="cart",catalog = "ecommerce") */
	@Table
	public class Cart implements java.io.Serializable {
		
		private Long id;
		private Long productId;
		private Long customerId;
		private Long quantity;
		private Long cost;
		private Products product;
		
		
		public Cart(Long id,Long productId,Long customerId,Long cost,Long quantity) {
			super();
			this.id=id;
			this.productId=productId;
			this.customerId=customerId;
			this.quantity=quantity;
			this.cost=cost;
		}
		public Cart(){}
 @Id
 @GeneratedValue(strategy=IDENTITY)
 @Column(name="ID", unique=true, nullable=false)	
 public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="PRODUCT_ID")
	public Long getProductId() {
		return this.productId;
	}
	public void setProductId(Long productId) {
		this.productId=productId;
	}
	
	@Column(name="CUSTOMER_ID")
	public Long getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId=customerId;
	}
	
	
	@Column(name="QUANTITY")
	public Long getQuantity() {
		return this.quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity=quantity;
	}
	
	
	@Transient
	@JsonProperty(access = Access.READ_ONLY)
	public Long getCost() {
		return cost;
	}
	public void setCost(Long cost) {
		this.cost = cost;
	}
	
	@Transient
	@JsonProperty(access = Access.READ_ONLY)
	public Products getProduct() {
		return product;
	}
	public void setProduct(Products product) {
		this.product = product;
	}
	
	
}
