package com.ecom.core.dto;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
/* @Table(name="reviews",catalog = "ecommerce") */
@Table
public class Reviews {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private Long id;
	
	@Column(name="PRODUCT_ID")
	private Long productId;
	@Column(name="USER_ID")
	private Long userId;
	@Column(name="USER_NAME")
	private String userName;
	@Column(name="EMAIL")
	private String email;
	@Column(name="SUBJECT")
	private String subject;
	@Column(name="DESCRIPTION",length = 1000)
	private String description;
	
	public Reviews() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Reviews(Long id, Long productId, Long userId, String userName, String email, String subject,
			String description) {
		super();
		this.id = id;
		this.productId = productId;
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.subject = subject;
		this.description = description;
	}




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
