package com.ecom.core.dto;

import java.util.List;

public class CategoriesWithSubCategories {
	String title;
	String type;
	boolean  active ;
	private List<Children> children;
	
	public CategoriesWithSubCategories() {
		
	}
	
	
	
	public CategoriesWithSubCategories(String title, String type, boolean active, List<Children> children) {
		super();
		this.title = title;
		this.type = type;
		this.active = active;
		this.children = children;
	}
	public List<Children> getChildren() {
		return children;
	}
	public void setChildren(List<Children> children) {
		this.children = children;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	

}
