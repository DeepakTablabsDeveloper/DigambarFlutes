package com.ecom.core.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


	
	@Entity
	/* @Table(name = "products" ,catalog = "ecommerce") */
	@Table
	public class Products{
		
		private Long id;
		private String name;
		private String description;
		private Long subCategoryId;
		private String productImage1;
		private String productImage2;
		private String productImage3;
		private String productImage4;
		List<String> imageList;
		private Long cost;
		private Long discountId;
		private Long costAfterDiscount;
		private Long brandId;
		private Long walletCreditAmt;
		private Long typeId;
		private List<ProductType> productTypeObj;
		private Categories brand;
		private Categories category;
		private String isAvailable;
		private Long noOfPieceSold;
		private Discount discount;
		
		public Products(){
			
		}



public Products(Long id, String name, String description, Long subCategoryId, String productImage1,
				String productImage2, String productImage3, String productImage4, List<String> imageList, Long cost,
				Long discountId, Long costAfterDiscount, Long brandId, Long walletCreditAmt, Long typeId,
				List<ProductType> productTypeObj, Categories brand, Categories category, String isAvailable,
				Long noOfPieceSold, Discount discount) {
			super();
			this.id = id;
			this.name = name;
			this.description = description;
			this.subCategoryId = subCategoryId;
			this.productImage1 = productImage1;
			this.productImage2 = productImage2;
			this.productImage3 = productImage3;
			this.productImage4 = productImage4;
			this.imageList = imageList;
			this.cost = cost;
			this.discountId = discountId;
			this.costAfterDiscount = costAfterDiscount;
			this.brandId = brandId;
			this.walletCreditAmt = walletCreditAmt;
			this.typeId = typeId;
			this.productTypeObj = productTypeObj;
			this.brand = brand;
			this.category = category;
			this.isAvailable = isAvailable;
			this.noOfPieceSold = noOfPieceSold;
			this.discount = discount;
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
	
	@Column(name="SUB_CATEGORY_ID")
	public Long getSubCategoryId() {
		return this.subCategoryId;
	}
	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
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
	/*
	@Column(name="RATING")
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	
	@Column(name="NO_OF_RATING")
	public Long getNoOfRating() {
		return noOfRating;
	}
	
	public void setNoOfRating(Long noOfRating) {
		this.noOfRating = noOfRating;
	}*/
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name="NO_OF_PIECE_SOLD")
	public Long getNoOfPieceSold() {
		return noOfPieceSold;
	}
	public void setNoOfPieceSold(Long noOfPieceSold) {
		this.noOfPieceSold = noOfPieceSold;
	}
		
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="IMAGE_1")
	 public String getProductImage1() {
		return productImage1;
	}

	public void setProductImage1(String productImage1) {
		this.productImage1 = productImage1;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="IMAGE_2")
	public String getProductImage2() {
		return productImage2;
	}

	public void setProductImage2(String productImage2) {
		this.productImage2 = productImage2;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="IMAGE_3")
	public String getProductImage3() {
		return productImage3;
	}

	public void setProductImage3(String productImage3) {
		this.productImage3 = productImage3;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name="IMAGE_4")
	public String getProductImage4() {
		return productImage4;
	}

	public void setProductImage4(String productImage4) {
		this.productImage4 = productImage4;
	}

	@Column(name="COST")
	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}


	@Transient
	@JsonProperty(access = Access.READ_WRITE)
	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	@Column(name="DISCOUNT_ID")
	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}
	
	@Column(name = "WALLET_CREDIT_AMT")
	public Long getWalletCreditAmt() {
		return walletCreditAmt;
	}

	public void setWalletCreditAmt(Long walletCreditAmt) {
		this.walletCreditAmt = walletCreditAmt;
	}

	@Column(name = "BRAND_ID")
	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	@Transient
	@JsonProperty(access = Access.READ_ONLY)
	public Categories getBrand() {
		return brand;
	}

	public void setBrand(Categories brand) {
		this.brand = brand;
	}

	@Transient
	@JsonProperty(access = Access.READ_ONLY)
	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	@Transient
	@JsonProperty(access = Access.READ_ONLY)
	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public Long getCostAfterDiscount() {
		return costAfterDiscount;
	}

	public void setCostAfterDiscount(Long costAfterDiscount) {
		this.costAfterDiscount = costAfterDiscount;
	}

	@Column
	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}


	@Transient
	public List<ProductType> getProductTypeObj() {
		return productTypeObj;
	}



	public void setProductTypeObj(List<ProductType> productTypeObj) {
		this.productTypeObj = productTypeObj;
	}

	
	
}
	


