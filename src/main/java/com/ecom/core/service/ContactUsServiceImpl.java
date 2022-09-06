package com.ecom.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.core.dto.ContactUs;
import com.ecom.core.repository.ContactUsRepository;

@Service
public class ContactUsServiceImpl implements ContactUsService {

	@Autowired
	ContactUsRepository repository;
	
	@Override
	public ContactUs saveContact(ContactUs contact) {
		return repository.save(contact);
	}

	@Override
	public List<ContactUs> getContact() {
		// TODO Auto-generated method stub
		return (List<ContactUs>) repository.findAll();
	}

}
