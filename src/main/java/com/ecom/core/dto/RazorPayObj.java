package com.ecom.core.dto;

public class RazorPayObj {

	private Orders order;
	private String signature;
	private String transactionId;
	private String razorPayOrderId;
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getRazorPayOrderId() {
		return razorPayOrderId;
	}
	public void setRazorPayOrderId(String razorPayOrderId) {
		this.razorPayOrderId = razorPayOrderId;
	}
	
	
}
