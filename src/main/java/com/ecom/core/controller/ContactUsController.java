package com.ecom.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.core.domain.JsonObjectFormat;
import com.ecom.core.dto.ContactUs;
import com.ecom.core.dto.Products;
import com.ecom.core.service.ContactUsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "ContactUsController", description = "Client and Customer Communication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ContactUsController {

	@Autowired
	ContactUsService contactUsService;

	@PostMapping("/contact/save")
	ResponseEntity<String> saveContactUs(@RequestBody ContactUs contact) throws JsonProcessingException {
         ContactUs contactUs=contactUsService.saveContact(contact);
         
         JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
         
			jsonobjectFormat.setMessage("Contact has been sent");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(contactUs);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
	}
	
	@GetMapping("/contact/getAll")
	ResponseEntity<String> getAllContacts(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
		if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);
		List<ContactUs> contactUs=contactUsService.getContact();
		Page<ContactUs> result= new PageImpl<ContactUs>(contactUs, pageable, contactUs.size());
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		if(!contactUs.isEmpty()) {
			jsonobjectFormat.setMessage("Contact has been sent");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(result);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}else {
			jsonobjectFormat.setMessage("No Data Found");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

}
