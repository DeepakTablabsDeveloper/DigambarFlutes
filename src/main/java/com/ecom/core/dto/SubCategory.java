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
	/* @Table(name="sub_category",catalog = "ecommerce") */
	@Table
	public class SubCategory implements java.io.Serializable {
		
		private Long id;
		private String name;
		private String description;
		private Long categoryId;
		private String categoryName;
		private String isAvailable;
		
		public SubCategory() {
			
		}
		
		public SubCategory(Long id, String name,Long categoryId, String description, Long category_id,String isAvailable) {
			super();
			this.id=id;	
			this.name=name;
			this.description=description;
			this.categoryId=categoryId;
			this.isAvailable=isAvailable;
		
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
	
	@Column(name="CATEGORY_ID")
	public Long getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description=description;
	}
	
	@Column(name="IS_AVAILABLE")
	public String getIsAvailable() {
		return this.isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable=isAvailable;
	}

	@Transient
	@JsonProperty(access = Access.READ_ONLY)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	
}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

