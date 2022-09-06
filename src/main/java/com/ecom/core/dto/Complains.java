package com.ecom.core.dto;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


	@Entity
	/* @Table(name="complains",catalog = "ecommerce") */
	@Table
	public class Complains implements java.io.Serializable {
		
		private Long id;
		private Long baughtProductId;
		private String reason;
		private String description;
		private String priority;
		private Long startDate;
		private Long endDate;
		
		public Complains() {
			
		}
		
		public Complains(Long id, Long baughtProductId,String reason, String description,
		String priority,Long start_date,Long end_date) {
			super();
			this.id=id;	
			this.baughtProductId=baughtProductId;
			this.reason=reason;
			this.description=description;
			this.priority=priority;
			this.startDate=startDate;
			this.endDate=endDate;
		}
@Id
@GeneratedValue(strategy=IDENTITY)
@Column(name="ID", unique=true, nullable=false)
public Long getId() {
	return this.id;
}
public void setId(Long id) {
	this.id = id;
}

@Column(name="BAUGHT_PRODUCT_ID")
public Long getBaughtProductId() {
	return this.baughtProductId;
}
public void setBaughtProductId(Long baughtProductId) {
	this.baughtProductId=baughtProductId;
}

@Column(name="REASON")
public String getReason() {
	return this.reason;
}
public void setReason(String reason) {
	this.reason=reason;
}

@Column(name="DESCRIPTION")
public String getDescription() {
	return this.description;
}
public void setDescription(String description) {
	this.description=description;}

	@Column(name="PRIORITY")
	public String getPriority() {
		return this.priority;
	}
	public void setPriority(String priority) {
		this.priority=priority;}
	
	@Column(name="START_DATE")
	public Long getStartDate() {
		return this.startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate=startDate;
	}

	@Column(name="END_DATE")
	public Long getEndDate() {
		return this.endDate;
	}
	public void setEndDate(Long endDate) {
		this.endDate=endDate;
	}


	}
		
