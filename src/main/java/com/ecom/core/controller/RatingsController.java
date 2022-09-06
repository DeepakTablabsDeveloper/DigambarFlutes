package com.ecom.core.controller;
/*
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.core.domain.JsonObjectFormat;
import com.ecom.core.dto.Customers;
import com.ecom.core.dto.Products;
import com.ecom.core.dto.Ratings;
import com.ecom.core.exception.CustomParameterizedException;
import com.ecom.core.repository.CustomersRepository;
import com.ecom.core.repository.ProductsRepository;
import com.ecom.core.repository.RatingsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;


@RestController
@Api(value = "RatingsController", description = "ratings details")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class RatingsController {

	private final Logger log = LoggerFactory.getLogger(RatingsController.class);
	
	@Autowired
	 RatingsRepository ratingsRepository;
	
	@Autowired
	ProductsRepository ProductsRepository;
	
	@Autowired
	CustomersRepository customersRepository;
	
	@PostMapping("/rating")
	public ResponseEntity<String> giveRatings(@RequestBody Ratings ratings) throws JsonProcessingException {
		log.debug("REST request to save rating Details : {}", ratings);
		if(ratings.getRating()>5||ratings.getRating()<1)
		{
			CustomParameterizedException customException = new CustomParameterizedException("rating should be between 1 to 5", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		if(ratings.getProductId()==null ||ratings.getCustomerId()==null)
		{
			CustomParameterizedException customException = new CustomParameterizedException("Customer or Product can't be null", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		Products products=ProductsRepository.findById(ratings.getProductId());
		Customers user =customersRepository.findById(ratings.getCustomerId());
		
		if(products==null||user==null)
		{
			CustomParameterizedException customException = new CustomParameterizedException("Customer or Product not available ", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		Ratings oldrating=ratingsRepository.findByProductIdAndCustomerId(ratings.getProductId(),ratings.getCustomerId());
		
		if(oldrating!=null)
		{	
			
			oldrating.setRating(ratings.getRating());
			Ratings newRating=ratingsRepository.save(oldrating);
			List<Ratings> listRating=ratingsRepository.findByProductId(products.getId());
			
			Float sum=new Float(0);
			for(int i=0;i<listRating.size();i++)
			{
				sum=listRating.get(i).getRating()+sum;
			}
			products.setRating(sum/products.getNoOfRating());
			ProductsRepository.save(products);
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("rating updated by customer");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(newRating);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		else
		{
			Ratings ratingDetails = ratingsRepository.save(ratings);
			System.out.println(ratingDetails.getRating());
			if(products.getRating()!=null)
			{
				
				products.setNoOfRating(products.getNoOfRating()+1);
				products.setRating(((ratingDetails.getRating()+products.getRating())/products.getNoOfRating()));
			
				
			}
			else{
				products.setRating(ratingDetails.getRating());
				products.setNoOfRating(new Long(1));
				
			}
			
			ProductsRepository.save(products);
		
		if (ratingDetails.getId() != null) {
			
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("rating saved successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(ratingDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
			}
		else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("rating not saved successfully");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		}
		
	 
	}
	@GetMapping("/rating/List")
	public ResponseEntity<String> getRatingsForProduct(@RequestParam("productId") Long productId) throws JsonProcessingException {
		List<Ratings> data = ratingsRepository.findByProductId(productId);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got all ratings for Product successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(data);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);

	}
	@GetMapping("/rating/comments")
	public ResponseEntity<String> getCommentsForProduct(@RequestParam("productId") Long productId) throws JsonProcessingException {
		List<Ratings> data = ratingsRepository.findByProductIdAndComment(productId);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got all ratings for Product successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(data);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);

	}
	

	@Transactional
	@DeleteMapping("/rating/user")
	public ResponseEntity<String> deleteRating(@RequestParam("productId") Long producId,@RequestParam("customerId") Long customerId, HttpServletRequest request)
			throws JsonProcessingException {
		
		try {
			ratingsRepository.deleteByProductIdAndCustomerId(producId , customerId);
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
	
	
	
	
}*/
