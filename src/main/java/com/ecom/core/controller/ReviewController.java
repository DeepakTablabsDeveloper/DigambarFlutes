package com.ecom.core.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ecom.core.dto.Reviews;
import com.ecom.core.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value="ReviewController", description="used to pass and view reviews") 
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ReviewController {
	
	private final Logger log = LoggerFactory.getLogger(ReviewController.class);
	
	@Autowired
	ReviewRepository ReviewRepository;
	
	@PostMapping("/review")
	public ResponseEntity<String> postReview(@RequestBody Reviews reqReview) throws JsonProcessingException {
		log.debug("REST request to post review : {}", reqReview);
		
		Reviews reviewValidate=ReviewRepository.findByUserIdAndProductId(reqReview.getUserId(),reqReview.getProductId());
		if(reviewValidate!=null)
		{
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("product already reviewed");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}	
		try {
			Reviews review=ReviewRepository.save(reqReview);
			// that means queue already exists
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("review posted successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(review);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		 } catch(Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("review post unsuccessfull ");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}
	
	
	@GetMapping("reviews/productId")
	public ResponseEntity<String> getAllReviewsForProduct(@RequestParam("productId")Long productId,@RequestParam(value = "page", required = false) Integer page,
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
		
		List<Reviews> obj = ReviewRepository.findByProductId(productId);
		if(!obj.isEmpty())
		{
			Page<Reviews> result= new PageImpl<>(obj, pageable, obj.size());
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Got all reviews for product");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(result);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("product reviews not found");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);	
		}
		
		
	}

	@GetMapping("reviews")
	public ResponseEntity<String> getAllReviews(@RequestParam(value = "page", required = false) Integer page,
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
		List<Reviews> obj = (List<Reviews>) ReviewRepository.findAll();		
		if(!obj.isEmpty())
		{
			
			Page<Reviews> result= new PageImpl<>(obj, pageable, obj.size());
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Got all reviews ");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(result);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("reviews not found");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);	
		}
		
		
	}
}
