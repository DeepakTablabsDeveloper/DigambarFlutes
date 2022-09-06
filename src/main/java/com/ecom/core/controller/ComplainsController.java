package com.ecom.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.core.util.SendEmailNotifications;
import com.ecom.core.domain.JsonObjectFormat;
import com.ecom.core.dto.Complains;
import com.ecom.core.dto.ContactUs;
import com.ecom.core.exception.CustomParameterizedException;
import com.ecom.core.repository.ComplainsRepository;
import com.ecom.core.repository.ContactUsRepository;
import com.ecom.core.repository.CustomersRepository;
import com.ecom.core.repository.ProductsRepository;
import com.ecom.core.util.FireBaseNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "ComplainsController", description = "Complains details")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ComplainsController {
	
	private final Logger log = LoggerFactory.getLogger(ComplainsController.class);

	@Autowired
	ComplainsRepository ComplainsDetailsRepository;
	
	@Autowired
	ProductsRepository productDetailsRepository;
	
	@Autowired
	FireBaseNotifications fireBaseNotifications;

	@Autowired
	CustomersRepository CustomersDetailsRepository;
	
	@Autowired
	SendEmailNotifications sendEmailNotifications;
	
	@Autowired
	ContactUsRepository contactUsRepository;	 

	@PostMapping("/Complains")
	public ResponseEntity<String> saveComplains(@RequestBody Complains Complains) throws JsonProcessingException {
		log.debug("REST request to save Complains : {}", Complains);
		Complains carDetails = ComplainsDetailsRepository.save(Complains);

		if (carDetails.getId() != null) {
			// that means queue already exists
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Complains saved successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(carDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Complains not saved successfully");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@PutMapping("/Complains")
	public ResponseEntity<String> updateComplains(@RequestBody Complains Complains) throws JsonProcessingException {
		log.debug("REST request to update Complains : {}", Complains);
		Complains temp=ComplainsDetailsRepository.findById(Complains.getId());
		if (temp == null) {
			CustomParameterizedException customException = new CustomParameterizedException("Invalid Id", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		
		
			if(Complains.getBaughtProductId()!=null)
				temp.setBaughtProductId(Complains.getBaughtProductId());
			if(Complains.getDescription()!=null)
				temp.setDescription(Complains.getDescription());
			if(Complains.getEndDate()!=null)
				temp.setEndDate(Complains.getEndDate());
			if(Complains.getPriority()!=null)
				temp.setPriority(Complains.getPriority());
			if(Complains.getReason()!=null)
				temp.setReason(Complains.getReason());
			if(Complains.getStartDate()!=null)
				temp.setStartDate(Complains.getStartDate());
			
		Complains ComplainsDetails = ComplainsDetailsRepository.save(temp);
		if (ComplainsDetails != null) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Complains updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(ComplainsDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Complains not updated successfully");
			jsonobjectFormat.setSuccess(false);
	
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@GetMapping("Complains")
	public ResponseEntity<String> getAllComplains(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) throws JsonProcessingException {
		if (size != null) {
		} else {
			size = 10;
		}
		if (page != null) {
		} else {
			page = 0;
		}
		Pageable pageable = new PageRequest(page, size);
		Page<Complains> obj = ComplainsDetailsRepository.findAll(pageable);
		if(obj.hasContent())
		{
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("got all complains successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(obj);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("complains not found");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}

	@GetMapping("/Complains/List")
	public ResponseEntity<String> getAllComplainsList() throws JsonProcessingException {
		List<Complains> data = ComplainsDetailsRepository.findAll();
		if(!data.isEmpty())
		{
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got all Complains Details successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(data);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("complains not found");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}

	@GetMapping("/Complains/Id/")
	public ResponseEntity<String> getComplainsBasedOnId(@RequestParam(value = "id", required = true) Long id) throws JsonProcessingException {
	
		log.debug("REST request to get Attendance : {}", id);
		Complains pageComplains = ComplainsDetailsRepository.findById(id);
		if(pageComplains!=null)
		{
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got Complains Details successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(pageComplains);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("complains not found");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@Transactional
	@DeleteMapping("/Complains")
	public ResponseEntity<String> deleteComplains(@RequestParam("id") Long id, HttpServletRequest request)
			throws JsonProcessingException {
		log.debug("REST request to delete Complains : {}", id);
		try {
			ComplainsDetailsRepository.deleteById(id);
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Recorded deleted successfully");
			jsonobjectFormat.setSuccess(true);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Recorded not deleted successfully");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	// Sending a query of user Email
    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(@RequestParam("emailAdd") String emailAdd, @RequestParam("message") String message) throws JsonProcessingException
    {
     	 // Try block to check for exceptions
    	try{
   		     sendEmailNotifications.sendContactUsMessage(emailAdd, message);
   		     contactUsRepository.save(new ContactUs(emailAdd, message));
   	 		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
   			 jsonobjectFormat.setMessage("Email sent successfully ...");
   			 jsonobjectFormat.setSuccess(true);
   	         ObjectMapper Obj = new ObjectMapper(); 
   	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
   		 	 return ResponseEntity.ok().body(customExceptionStr);
   		 	 
   		}catch(Exception e){
   			e.printStackTrace();
   			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
   			 jsonobjectFormat.setMessage("Something went wrong unable to send query mail");
   			 jsonobjectFormat.setSuccess(false);
   	         ObjectMapper Obj = new ObjectMapper(); 
   	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
   		 	 return ResponseEntity.ok().body(customExceptionStr);
   		}
    }
}
