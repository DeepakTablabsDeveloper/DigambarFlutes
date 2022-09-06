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
	/* @Table(name="baught_products",catalog = "ecommerce") */
	@Table
	public class BaughtProducts implements java.io.Serializable {
		private Long id;
		private Long productId;
		private Products product;
		private Long cost;
		private Long quantity;
		private Long orderId;
		
		public BaughtProducts() {
			
		}
		
		public BaughtProducts(Long id,Long productId,Long cost,
				Long quantity, Long orderId) {
		super();
		this.id=id;
		this.productId=productId;
		this.cost=cost;
		this.quantity=quantity;
		this.orderId=orderId;
	}
		
		@Id
		@GeneratedValue(strategy = IDENTITY)
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
		
		@Column(name="COST")
		public Long getCost() {
			return this.cost;
		}
		public void setCost(Long cost) {
			this.cost=cost;
		}
		
		@Column(name="QUANTITY")
		public Long getQuantity() {
			return this.quantity;
		}
		public void setQuantity(Long quantity) {
			this.quantity=quantity;
		}
		
		@JsonProperty(access = Access.READ_ONLY)
		@Column(name="ORDER_ID")
		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		@JsonProperty(access = Access.READ_ONLY)
		@Transient
		public Products getProduct() {
			return product;
		}

		public void setProduct(Products product) {
			this.product = product;
		}
		
		
		
		

	}