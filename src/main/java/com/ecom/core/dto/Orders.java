package com.ecom.core.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
/* @Table(name="orders",catalog = "ecommerce") */
@Table
public class Orders {
	
	private Long id;
	private Long customerId;
	private String orderId;
	private String orderDate;
	private Long orderTotal;
	private Long offerPrice;
	private Long walletAmtUsed;
	private Long amtPaid;
	private String address;
	private String customerName;
	private String status;
	private String deliveryDate;
	private String deliveryPersonName;
	private String deliverPersonMobileNumber;
	List<BaughtProducts> baughtProducts;
	private Long productConfigId;
//	@JsonProperty(access = Access.READ_ONLY)
	private ProductConfiguration configuration;
	public Orders()
	{
	}
	
	public Orders(Long id, Long customerId, String orderDate, Long orderTotal, Long offerPrice, Long walletAmtUsed,
			Long amtPaid, String address, String customerName, String status, String deliveryDate,
			String deliveryPersonName, String deliverPersonMobileNumber,Long productConfigId,ProductConfiguration configuration) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.orderTotal = orderTotal;
		this.offerPrice = offerPrice;
		this.walletAmtUsed = walletAmtUsed;
		this.amtPaid = amtPaid;
		this.address = address;
		this.customerName = customerName;
		this.status = status;
		this.deliveryDate = deliveryDate;
		this.deliveryPersonName = deliveryPersonName;
		this.deliverPersonMobileNumber = deliverPersonMobileNumber;
		this.productConfigId=productConfigId;
		this.configuration=configuration;
	}



	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="ORDER_DATE")
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}


	@Column(name="ORDER_TOTAL")
	public Long getOrderTotal() {
		return orderTotal;
	}


	public void setOrderTotal(Long orderTotal) {
		this.orderTotal = orderTotal;
	}

	@Column(name="ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Column(name="CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long userId) {
		this.customerId = userId;
	}

	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="DELIVERY_DATE")
	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Column(name="OFFER_PRICE")
	public Long getOfferPrice() {
		return offerPrice;
	}

	
	public void setOfferPrice(Long offerPrice) {
		this.offerPrice = offerPrice;
	}

	@Column(name="WALLET_AMT_USED")
	public Long getWalletAmtUsed() {
		return walletAmtUsed;
	}

	public void setWalletAmtUsed(Long walletAmtUsed) {
		this.walletAmtUsed = walletAmtUsed;
	}

	@Column(name="AMT_PAID")
	public Long getAmtPaid() {
		return amtPaid;
	}

	public void setAmtPaid(Long amtPaid) {
		this.amtPaid = amtPaid;
	}

	@Column(name="DELIVERY_PERSON_NAME")
	public String getDeliveryPersonName() {
		return deliveryPersonName;
	}

	public void setDeliveryPersonName(String deliveryPersonName) {
		this.deliveryPersonName = deliveryPersonName;
	}

	@Column(name="DELIVERY_PERSON_MOBILE_NUMBER")
	public String getDeliverPersonMobileNumber() {
		return deliverPersonMobileNumber;
	}

	public void setDeliverPersonMobileNumber(String deliverPersonMobileNumber) {
		this.deliverPersonMobileNumber = deliverPersonMobileNumber;
	}

	@Transient
	public List<BaughtProducts> getBaughtProducts() {
		return baughtProducts;
	}

	public void setBaughtProducts(List<BaughtProducts> baughtProducts) {
		this.baughtProducts = baughtProducts;
	}

	@Column(name="ORDER_ID")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name="PROD_CONFIG_ID")
	public Long getProductConfigId() {
		return productConfigId;
	}

	public void setProductConfigId(Long productConfigId) {
		this.productConfigId = productConfigId;
	}
    
	@Transient
	public ProductConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(ProductConfiguration configuration) {
		this.configuration = configuration;
	}
	
	
}
