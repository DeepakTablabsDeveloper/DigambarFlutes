package com.ecom.core.service;

import java.util.List;

import com.ecom.core.dto.ContactUs;

public interface ContactUsService {

	ContactUs saveContact(ContactUs contact);

	List<ContactUs> getContact();
	
}
