package com.ecom.core.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
/* @Table(name="bills",catalog = "ecommerce") */
@Table
public class Bills {
	private Long id;
	private Blob bill;
	
	
	public Bills(Long id, Blob bill) {
		super();
		this.id = id;
		this.bill = bill;
	}


	@Id
	@GeneratedValue(strategy =IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Column(name="BILL")
	public Blob getBill() {
		return bill;
	}


	public void setBill(Blob bill) {
		this.bill = bill;
	}
	
	
	
	

}
