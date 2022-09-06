package com.ecom.core.dto;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;



	
	@Entity
	/* @Table(name="discount",catalog = "ecommerce") */
	@Table
	public class Discount implements java.io.Serializable {
		
		private Long id;
		private String name;
		private String description;
		
		
		private Long percentageOfBaseCost;
		//private String startDate;
		//private String endDate;
		
		public Discount() {
			
		}
		
		public Discount(Long id,String name,String description,Long percentageOfBaseCost/*,String startDate, String endDate*/) {
			super();
			this.id=id;	
			this.name=name;
			this.description=description;
			
		
			this.percentageOfBaseCost=percentageOfBaseCost;	
			//this.startDate=startDate;
			//this.endDate=endDate;
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

@Column(name="NAME")
public String getName() {
	return this.name;
}
public void setName(String name) {
	this.name=name;
}

@Column(name="DESCRIPTION")
public String getDescription() {
	return this.description;
}
public void setDescription(String description) {
	this.description=description;
}


@Column(name="PERCENTAGE_OF_BASE_COST")
public Long getPercentageOfBaseCost() {
	return this.percentageOfBaseCost;
}


public void setPercentageOfBaseCost(Long percentageOfBaseCost) {
	this.percentageOfBaseCost =percentageOfBaseCost;
}
/*
@Column(name="START_DATE")
public String getStartDate() {
	return this.startDate;
}

public void setStartDate(String startDate) {
	this.startDate = startDate;
}

@Column(name="END_DATE")
public String getEndDate() {
	return this.endDate;
}

public void setEndDate(String endDate) {
	this.endDate = endDate;
}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
