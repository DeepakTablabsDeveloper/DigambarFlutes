package com.ecom.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecom.core.dto.ProductType;
import com.ecom.core.repository.ProductTypeRepository;
import com.ecom.core.domain.JsonObjectFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value="ProductsTypeController", description="Products type details") 
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ProductTypeController {

	@Autowired
	ProductTypeRepository productTypeRepository;
	
	//This api is used to display all the registered productTypes in database
			@GetMapping("/productType/getAll")
			public ResponseEntity<String> getAllProductType() throws JsonProcessingException {
				
				try {
					 List<ProductType> typeList =(List<ProductType>) productTypeRepository.findAll();
					 
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 jsonobjectFormat.setMessage("Product Type Details fetched successfully");
					 jsonobjectFormat.setSuccess(true);
					 jsonobjectFormat.setData(typeList);
			         ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			         return ResponseEntity.ok().body(customExceptionStr);
				} catch(Exception e) {
					
					 JsonObjectFormat jsonObject = new JsonObjectFormat();
					 
					 jsonObject.setMessage("No product type data to show");
					 jsonObject.setSuccess(false);

						ObjectMapper mapperObject = new ObjectMapper();
						String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
						return ResponseEntity.ok().body(displayResponse);
		
					
					
				}
			}
			
			
		
			
			//This api is used to add new product type data in the database
			@PostMapping("/productType/save")
			public ResponseEntity<String> saveProductType(@RequestBody ProductType productType)  throws JsonProcessingException {

				try {
					
					ProductType newType = productTypeRepository.save(productType);
					
					JsonObjectFormat jsonObject = new JsonObjectFormat();
					 
					jsonObject.setData(newType);
					jsonObject.setMessage("Product type data saved successfully");
					jsonObject.setSuccess(true);

					ObjectMapper mapperObject = new ObjectMapper();
					String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
					return ResponseEntity.ok().body(displayResponse);
		
				} catch(Exception e) {
					
					 JsonObjectFormat jsonObject = new JsonObjectFormat();
					 
					 jsonObject.setMessage("No product type data saved");
					 jsonObject.setSuccess(false);
					 jsonObject.setData("");

					 ObjectMapper mapperObject = new ObjectMapper();
					 String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
					 return ResponseEntity.ok().body(displayResponse);
				
				}

			}
			
			//This api is used to get particular productType by productType_id in database
			@GetMapping("/productType/getById")
			public ResponseEntity<String> getproductTypeById(@RequestParam("productType_id") Long productType_id) throws JsonProcessingException{
					
				try {
					if(!(productType_id != null)){
						JsonObjectFormat jsonObject = new JsonObjectFormat();
						 
						jsonObject.setData("");
						jsonObject.setMessage("Invalid productType Id");
						jsonObject.setSuccess(false);
						
			            ObjectMapper Obj = new ObjectMapper(); 
					    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
				 		return ResponseEntity.ok().body(customExceptionStr);
						
					} else {
					 
					ProductType newproductType = productTypeRepository.findOne(productType_id);
					JsonObjectFormat jsonObject = new JsonObjectFormat();
					 
					jsonObject.setData(newproductType);
					jsonObject.setMessage("productType data fetched successfully");
					jsonObject.setSuccess(true);

					ObjectMapper mapperObject = new ObjectMapper();
					String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
					return ResponseEntity.ok().body(displayResponse);
					
					}
				
		
				} catch(Exception e) {
					
					 JsonObjectFormat jsonObject = new JsonObjectFormat();
					 
					 jsonObject.setMessage("No productType data with the productType_id "+ productType_id);
					 jsonObject.setSuccess(false);
					 jsonObject.setData("");

					 ObjectMapper mapperObject = new ObjectMapper();
					 String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
					 return ResponseEntity.ok().body(displayResponse);
				
				}
				
			}
			
			//This api is used to update  Product Type data in database
					@PostMapping("/productType/update")
					public ResponseEntity<String> updateProductType(@RequestBody ProductType productType)  throws JsonProcessingException {

						try {
							
							String defaultStr ="string";
							if(productType.getId()==0 || (Long)productType.getId() == null) {
								JsonObjectFormat jsonObject = new JsonObjectFormat();
								 
								jsonObject.setData("");
								jsonObject.setMessage("Invalid poduct type Id");
								jsonObject.setSuccess(false);
								
					            ObjectMapper Obj = new ObjectMapper(); 
							    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
						 		return ResponseEntity.ok().body(customExceptionStr);
								
							} else if (productTypeRepository.exists(productType.getId())){
								
								ProductType newproductType = productTypeRepository.findOne(productType.getId());
								
								if((Long)productType.getId() != null) {
									newproductType.setId(productType.getId());
								}
								if(productType.getType() != null  && !productType.getType().equalsIgnoreCase(defaultStr)) {
									newproductType.setType(productType.getType());
								}
						
								ProductType updatedproductType = productTypeRepository.save(newproductType);
							
							JsonObjectFormat jsonObject = new JsonObjectFormat();
							 
							jsonObject.setData(updatedproductType);
							jsonObject.setMessage("productType data updated successfully");
							jsonObject.setSuccess(true);

							ObjectMapper mapperObject = new ObjectMapper();
							String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
							return ResponseEntity.ok().body(displayResponse);
							
							} else {
								JsonObjectFormat jsonObject = new JsonObjectFormat();
								 
								jsonObject.setData("");
								jsonObject.setMessage("productType data with given id does not exist ");
								jsonObject.setSuccess(false);
								
					            ObjectMapper Obj = new ObjectMapper(); 
							    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
						 		return ResponseEntity.ok().body(customExceptionStr);
								
							}
							
						} catch(Exception e) {
							
							 JsonObjectFormat jsonObject = new JsonObjectFormat();
							 
							 jsonObject.setMessage("No productType data saved");
							 jsonObject.setSuccess(false);
							 jsonObject.setData("");

							 ObjectMapper mapperObject = new ObjectMapper();
							 String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
							 return ResponseEntity.ok().body(displayResponse);
						
						}

					}
			

			//API is used to delete particular productType by productType id in database
			@DeleteMapping("/productType/deleteById")
			public ResponseEntity<String> deleteProductType(@RequestParam("productType_id") Long productType_id) throws JsonProcessingException {

				try {
					ProductType deleteproductType  = productTypeRepository.findOne(productType_id);
					if (deleteproductType != null) {
						productTypeRepository.delete(productType_id);

						JsonObjectFormat jsonObject = new JsonObjectFormat();
						 
						jsonObject.setData(deleteproductType);
						jsonObject.setMessage("Product Type data deleted successfully.");
						jsonObject.setSuccess(true);

						ObjectMapper mapperObject = new ObjectMapper();
						String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
						return ResponseEntity.ok().body(displayResponse);

					}

					else {

						JsonObjectFormat jsonObject = new JsonObjectFormat();
						 
						jsonObject.setData(deleteproductType);
						jsonObject.setMessage("Product Type data could not be deleted.");
						jsonObject.setSuccess(false);

						ObjectMapper mapperObject = new ObjectMapper();
						String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
						return ResponseEntity.ok().body(displayResponse);

					}

				} catch (Exception e) {
					// TODO: handle exception


					JsonObjectFormat jsonObject = new JsonObjectFormat();
				
					jsonObject.setMessage("productType data could not be deleted.");
					jsonObject.setSuccess(false);
					jsonObject.setData("");
					 
					ObjectMapper mapperObject = new ObjectMapper();
					String displayResponse = mapperObject.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
					return ResponseEntity.ok().body(displayResponse);

				}

			}
			
					
}
