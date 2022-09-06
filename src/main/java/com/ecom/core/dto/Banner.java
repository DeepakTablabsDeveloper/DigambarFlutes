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
/*
 * @Table(name="banner",catalog = "ecommerce")
 */@Table
public class Banner {
	private Long id;

	private String title;
	private String subTitle;
	private String image;
	private Long categoryId;
	private Categories category;
	
	
	
	
	public Banner(Long id, String title, String subTitle, String image, Long categoryId, Categories category) {
		super();
		this.id = id;
		this.title = title;
		this.subTitle = subTitle;
		this.image = image;
		this.categoryId = categoryId;
		this.category = category;
	}
	
	public Banner() {
		super();
		// TODO Auto-generated constructor stub
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
	
	@Transient
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Transient
	@Column(name="SUB_TITLE")
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	@Column(name="IMAGE")
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Transient
	@Column(name="CATEGORY_ID")
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public Categories getCategory() {
		return category;
	}
	public void setCategory(Categories category) {
		this.category = category;
	}
	

}
