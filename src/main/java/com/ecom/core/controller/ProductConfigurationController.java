package com.ecom.core.controller;

import java.util.List;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.CrossOrigin;	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.RestController;
	import com.ecom.core.dto.ProductConfiguration;
	import com.ecom.core.repository.ProductConfigurationRepository;
				
	import com.ecom.core.domain.JsonObjectFormat;
	import com.fasterxml.jackson.core.JsonProcessingException;
	import com.fasterxml.jackson.databind.ObjectMapper;

	import io.swagger.annotations.Api;

	@RestController
	@Api(value="ProductConfigurationController", description="Products type details") 
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/api")
	public class ProductConfigurationController  {

		@Autowired
		ProductConfigurationRepository productConfigurationRepository;
		
		//This api is used to display all the registered Product Configurations in database
				@GetMapping("/productconfiguration/getAll")
				public ResponseEntity<String> getAllProductConfiguration() throws JsonProcessingException {
					
					try {
						 List<ProductConfiguration> typeList =(List<ProductConfiguration>) productConfigurationRepository.findAll();
						 
						 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
						 jsonobjectFormat.setMessage("Product Configuration Details fetched successfully");
						 jsonobjectFormat.setSuccess(true);
						 jsonobjectFormat.setData(typeList);
				         ObjectMapper Obj = new ObjectMapper(); 
				         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				         return ResponseEntity.ok().body(customExceptionStr);
					} catch(Exception e) {
						
						 JsonObjectFormat jsonObject = new JsonObjectFormat();
						 
						 jsonObject.setMessage("No product configuration data to show");
						 jsonObject.setSuccess(false);

							ObjectMapper mapperObject = new ObjectMapper();
							String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
							return ResponseEntity.ok().body(displayResponse);
			
						
						
					}
				}
				
				
			
				
				//This api is used to add new product configuration data in the database
				@PostMapping("/productconfiguration/save")
				public ResponseEntity<String> saveproductConfiguration(@RequestBody ProductConfiguration productConfiguration)  throws JsonProcessingException {

					try {
						
						ProductConfiguration newType = productConfigurationRepository.save(productConfiguration);
						
						JsonObjectFormat jsonObject = new JsonObjectFormat();
						 
						jsonObject.setData(newType);
						jsonObject.setMessage("Product configuration data saved successfully");
						jsonObject.setSuccess(true);

						ObjectMapper mapperObject = new ObjectMapper();
						String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
						return ResponseEntity.ok().body(displayResponse);
			
					} catch(Exception e) {
						
						 JsonObjectFormat jsonObject = new JsonObjectFormat();
						 
						 jsonObject.setMessage("No product configuration data saved");
						 jsonObject.setSuccess(false);
						 jsonObject.setData("");

						 ObjectMapper mapperObject = new ObjectMapper();
						 String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
						 return ResponseEntity.ok().body(displayResponse);
					
					}

				}
				
				//This api is used to get particular productConfiguration by productConfiguration_id in database
				@GetMapping("/productconfiguration/getById")
				public ResponseEntity<String> getproductConfigurationById(@RequestParam("customer_id") Long customer_id) throws JsonProcessingException{
						
					try {
						if(!(customer_id != null)){
							JsonObjectFormat jsonObject = new JsonObjectFormat();
							 
							jsonObject.setData("");
							jsonObject.setMessage("Invalid customer Id");
							jsonObject.setSuccess(false);
							
				            ObjectMapper Obj = new ObjectMapper(); 
						    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
					 		return ResponseEntity.ok().body(customExceptionStr);
							
						} else {
						 
						ProductConfiguration newProductConfiguration = productConfigurationRepository.findByCustomerId(customer_id);
						if(newProductConfiguration == null) {
							JsonObjectFormat jsonObject = new JsonObjectFormat();
							jsonObject.setMessage("No Product Configuration data for the customer_id");
							jsonObject.setSuccess(true);

							ObjectMapper mapperObject = new ObjectMapper();
							String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
							return ResponseEntity.ok().body(displayResponse);
							
						} else {
							
						JsonObjectFormat jsonObject = new JsonObjectFormat();
							jsonObject.setData(newProductConfiguration);

						jsonObject.setMessage("Product Configuration data fetched successfully");
						jsonObject.setSuccess(true);

						ObjectMapper mapperObject = new ObjectMapper();
						String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
						return ResponseEntity.ok().body(displayResponse);
						
						}
					}
					
			
					} catch(Exception e) {
						
						 JsonObjectFormat jsonObject = new JsonObjectFormat();
						 
						 jsonObject.setMessage("No Product Configuration data with the customer_Id "+ customer_id);
						 jsonObject.setSuccess(false);
						 jsonObject.setData(e.getStackTrace());

						 ObjectMapper mapperObject = new ObjectMapper();
						 String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
						 return ResponseEntity.ok().body(displayResponse);
					
					}
					
				}
				
				//This api is used to update  Product Configuration data in database
						@PostMapping("/productconfiguration/update")
						public ResponseEntity<String> updateproductConfiguration(@RequestBody ProductConfiguration productConfiguration)  throws JsonProcessingException {

							try {
								
								String defaultStr ="string";
								if(productConfiguration.getId()==0 || (Long)productConfiguration.getId() == null) {
									JsonObjectFormat jsonObject = new JsonObjectFormat();
									 
									jsonObject.setData("");
									jsonObject.setMessage("Invalid poduct configuration Id");
									jsonObject.setSuccess(false);
									
						            ObjectMapper Obj = new ObjectMapper(); 
								    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
							 		return ResponseEntity.ok().body(customExceptionStr);
									
								} else if (productConfigurationRepository.exists(productConfiguration.getId())){
									
									ProductConfiguration newProductConfiguration = productConfigurationRepository.findOne(productConfiguration.getId());
									
									if((Long)productConfiguration.getId() != null) {
										newProductConfiguration.setId(productConfiguration.getId());
									}
			
									if((Long)productConfiguration.getCustomerId() != null) {
										newProductConfiguration.setCustomerId(productConfiguration.getCustomerId());
									}
									if((Long)productConfiguration.getProductId() != null) {
										newProductConfiguration.setProductId(productConfiguration.getProductId());
									}
									if(productConfiguration.getHandSpecification() != null  && !productConfiguration.getHandSpecification().equalsIgnoreCase(defaultStr)) {
										newProductConfiguration.setHandSpecification(productConfiguration.getHandSpecification());
									}
									if(productConfiguration.getFrequency() != null  && !productConfiguration.getFrequency().equalsIgnoreCase(defaultStr)) {
										newProductConfiguration.setFrequency(productConfiguration.getFrequency());
									}
									if(productConfiguration.getGuruName() != null  && !productConfiguration.getGuruName().equalsIgnoreCase(defaultStr)) {
										newProductConfiguration.setGuruName(productConfiguration.getGuruName());
									}
									if(productConfiguration.getThread1() != null  && !productConfiguration.getThread1().equalsIgnoreCase(defaultStr)) {
										newProductConfiguration.setThread1(productConfiguration.getThread1());
									}
									if(productConfiguration.getThread2() != null  && !productConfiguration.getThread2().equalsIgnoreCase(defaultStr)) {
										newProductConfiguration.setThread2(productConfiguration.getThread2());
									}
									if(productConfiguration.getCreatedOn() != null) {
										newProductConfiguration.setCreatedOn(productConfiguration.getCreatedOn());
									}
									
							
									ProductConfiguration updatedproductConfiguration = productConfigurationRepository.save(newProductConfiguration);
								
								JsonObjectFormat jsonObject = new JsonObjectFormat();
								 
								jsonObject.setData(updatedproductConfiguration);
								jsonObject.setMessage("Product Configuration data updated successfully");
								jsonObject.setSuccess(true);

								ObjectMapper mapperObject = new ObjectMapper();
								String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
								return ResponseEntity.ok().body(displayResponse);
								
								} else {
									JsonObjectFormat jsonObject = new JsonObjectFormat();
									 
									jsonObject.setData("");
									jsonObject.setMessage("Product Configuration data with given id does not exist ");
									jsonObject.setSuccess(false);
									
						            ObjectMapper Obj = new ObjectMapper(); 
								    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
							 		return ResponseEntity.ok().body(customExceptionStr);
									
								}
								
							} catch(Exception e) {
								
								 JsonObjectFormat jsonObject = new JsonObjectFormat();
								 
								 jsonObject.setMessage("No Product Configuration data saved");
								 jsonObject.setSuccess(false);
								 jsonObject.setData("");

								 ObjectMapper mapperObject = new ObjectMapper();
								 String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
								 return ResponseEntity.ok().body(displayResponse);
							
							}

						}
						
	}

