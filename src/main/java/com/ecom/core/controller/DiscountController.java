package com.ecom.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

import com.ecom.core.domain.JsonObjectFormat;
import com.ecom.core.dto.Discount;
import com.ecom.core.exception.CustomParameterizedException;
import com.ecom.core.repository.DiscountRepository;
import com.ecom.core.util.FireBaseNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "DiscountController", description = "Discount details")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class DiscountController {
	
	private final Logger log = LoggerFactory.getLogger(DiscountController.class);

	@Autowired
	DiscountRepository DiscountDetailsRepository;
	
	
	
	@Autowired
	FireBaseNotifications fireBaseNotifications;

	

	@PostMapping("/Discount")
	public ResponseEntity<String> saveDiscount(@RequestBody Discount Discount) throws JsonProcessingException {
		log.debug("REST request to save Discount Details : {}", Discount);
		Discount carDetails = DiscountDetailsRepository.save(Discount);

		if (carDetails.getId() != null) {
			// that means queue already exists
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Discount saved successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(carDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Discount not saved successfully");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@PutMapping("/Discount")
	public ResponseEntity<String> updateDiscount(@RequestBody Discount Discount) throws JsonProcessingException {
		log.debug("REST request to update Discount : {}", Discount);
		if (!(Discount.getId() != null)) {
			CustomParameterizedException customException = new CustomParameterizedException("Invalid Id", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}

		Discount temp=DiscountDetailsRepository.findById(Discount.getId());
		if (temp.getId() != null) {
			
			if(Discount.getDescription()!=null)
				temp.setDescription(Discount.getDescription());
			
			
			if(Discount.getName()!=null)
				temp.setName(Discount.getName());
			if(Discount.getPercentageOfBaseCost()!=null)
				temp.setPercentageOfBaseCost(Discount.getPercentageOfBaseCost());
			
			
		Discount DiscountDetails = DiscountDetailsRepository.save(temp);
			// that means queue already exists
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Discount Updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(DiscountDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Discount not updated successfully");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@GetMapping("Discount")
	public ResponseEntity<String> getAllDiscount(@RequestParam(value = "page", required = false) Integer page,
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
		Page<Discount> obj = DiscountDetailsRepository.findAll(pageable);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got All Discounts successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(obj);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		
	}

	@GetMapping("/Discount/List")
	public ResponseEntity<String> getAllDiscountList() throws JsonProcessingException {
		List<Discount> data = DiscountDetailsRepository.findAll();
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got all Discount Details successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(data);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);

	}

	@GetMapping("/Discount/Id")
	public ResponseEntity<String> getDiscountBasedOnId(@RequestParam(value = "id", required = true) Long id) throws JsonProcessingException {
		

		log.debug("REST request to get Discount : {}", id);
		Discount discount = DiscountDetailsRepository.findById(id);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got Discount Details successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(discount);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
	}

	@Transactional
	@DeleteMapping("/Discount")
	public ResponseEntity<String> deleteDiscount(@RequestParam("id") Long id, HttpServletRequest request)
			throws JsonProcessingException {
		log.debug("REST request to delete Discount : {}", id);
		try {
			DiscountDetailsRepository.deleteById(id);
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Recorded deleted successfully");
			jsonobjectFormat.setSuccess(true);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Recorded not deleted successfully");
			jsonobjectFormat.setSuccess(true);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}


}
