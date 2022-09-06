package com.ecom.core.dto;

public class Children {
	String path;
	String title;
	String type;
	
	public Children() {
		
	}
	
	public Children(String path, String title, String type) {
		super();
		this.path = path;
		this.title = title;
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	
	

}
