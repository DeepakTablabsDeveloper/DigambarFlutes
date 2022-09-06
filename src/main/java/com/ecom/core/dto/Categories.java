package com.ecom.core.dto;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



	@Entity
	/* @Table(name="categories",catalog = "ecommerce") */
	@Table
	public class Categories implements java.io.Serializable {
		
		private Long id;
		private Long staticId;
		private String name;
		private String image;
		private String description;
		private String isAvailable;
	
		
		public Categories() {
			
		}
			
 public Categories(Long id, Long staticId, String name, String image, String description, String isAvailable) {
			super();
			this.id = id;
			this.staticId = staticId;
			this.name = name;
			this.image = image;
			this.description = description;
			this.isAvailable = isAvailable;
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
	
	@Column(name="IS_AVAILABLE")
	public String getIsAvailable() {
		return this.isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable=isAvailable;
	}

	@Column(name="STATIC_ID")
	public Long getStaticId() {
		return staticId;
	}

	public void setStaticId(Long staticId) {
		this.staticId = staticId;
	}

	@Column(name="IMAGE")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	
}	



