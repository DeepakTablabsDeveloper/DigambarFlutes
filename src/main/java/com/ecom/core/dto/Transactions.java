package com.ecom.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
/* @Table(name="transactions",catalog = "ecommerce") */
@Table
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", unique=true, nullable=false)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "signature")
    private String signature;

    @Column(name = "time")
    private String time;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "razor_pay_order_id")
    private String razorPayOrderId;

    @Column(name = "status")
    private String status;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "type")
    private String type;

    @Column(name = "user_id")
    private Long userId;
    
    
    

	public Transactions() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	public Transactions(Long amount, String signature, String time, Long orderId, String razorPayOrderId,
			String status, String transactionDate, String transactionId, String type, Long userId) {
		super();
	
		this.amount = amount;
		this.signature = signature;
		this.time = time;
		this.orderId = orderId;
		this.razorPayOrderId = razorPayOrderId;
		this.status = status;
		this.transactionDate = transactionDate;
		this.transactionId = transactionId;
		this.type = type;
		this.userId = userId;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getRazorPayOrderId() {
		return razorPayOrderId;
	}

	public void setRazorPayOrderId(String razorPayOrderId) {
		this.razorPayOrderId = razorPayOrderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    
    
}
