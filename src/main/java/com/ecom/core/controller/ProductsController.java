package com.ecom.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import org.springframework.web.multipart.MultipartFile;

import com.ecom.core.domain.JsonObjectFormat;
import com.ecom.core.dto.Categories;
import com.ecom.core.dto.Discount;
import com.ecom.core.dto.ProductType;
import com.ecom.core.dto.Products;
import com.ecom.core.exception.CustomParameterizedException;
import com.ecom.core.repository.CategoriesRepository;
import com.ecom.core.repository.DiscountRepository;
import com.ecom.core.repository.ProductTypeRepository;
import com.ecom.core.repository.ProductsRepository;
import com.ecom.core.util.FireBaseNotifications;
import com.ecom.core.util.AmazonClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;


@RestController
@Api(value="ProductsController", description="Products details") 
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ProductsController {
	
	private final Logger log = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	ProductsRepository productsRepository;

	@Autowired
	FireBaseNotifications fireBaseNotifications;
	
	@Autowired
	CategoriesRepository CategoriesRepository;
	
	@Autowired
	DiscountRepository DiscountRepository;
	
	@Autowired
	ProductTypeRepository ProductTypeRepository;
	
	@Autowired
	AmazonClient amazonClient;
	
	@PostMapping("/Products")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<String> saveProducts(@RequestBody Products Products) throws JsonProcessingException {
		log.debug("REST request to save Bike Details : {}", Products);
		Categories brand=CategoriesRepository.findById(Products.getBrandId());
		if(brand==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("Brand Not Available", "500");
            ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
	 		return ResponseEntity.ok().body(customExceptionStr);
		}
		Categories categories=CategoriesRepository.findById(Products.getSubCategoryId());
		if(categories==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("Category Not Available", "500");
            ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
	 		return ResponseEntity.ok().body(customExceptionStr);
		}
	   	List<String> images=Products.getImageList();
				 if(images.size()!=0)
				 {
					 for(int j=0;j<images.size();j++)
					 {
						 if(j==0)
							

							 Products.setProductImage1(Products.getImageList().get(j));
						 else if(j==1)
							 Products.setProductImage2(Products.getImageList().get(j));
						 else if(j==2)
							 Products.setProductImage3(Products.getImageList().get(j));
						 else if(j==3)
							 Products.setProductImage4(Products.getImageList().get(j));
				 }
			}
		 
		 
		 try{
			 Products productDetails= productsRepository.save(Products);
			 productDetails.setBrand(brand);
			 productDetails.setCategory(categories);
			 if(Products.getDiscountId()!=null)
			 {
				Discount  discount= DiscountRepository.findById(productDetails.getDiscountId());
				Long discountPercent=discount.getPercentageOfBaseCost();
				Long productCost=productDetails.getCost();
				double d1=(double)discountPercent;
				double price=productCost*(d1/100);
				Long lPrice=(long)price;
				productDetails.setCostAfterDiscount(productDetails.getCost()-lPrice);
				 productDetails.setDiscount(discount);
			 }
			 
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Product saved successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(productDetails);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }catch(Exception e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Product not saved successfully in exception");
			 jsonobjectFormat.setData(e.getStackTrace());
			 jsonobjectFormat.setSuccess(false);
			
	         ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		     return ResponseEntity.ok().body(customExceptionStr);
		 }
		 
		
	}
	@PutMapping("/Products")
	public ResponseEntity<String> updateProducts(@RequestBody Products Products) throws JsonProcessingException  {
		log.debug("REST request to update Attendance : {}", Products);
		Products temp=productsRepository.findById(Products.getId());
		if (temp==null||Products.getId() == null) {
			 CustomParameterizedException customException=new CustomParameterizedException("Invalid Id", "500");
	            ObjectMapper Obj = new ObjectMapper(); 
				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 		return ResponseEntity.ok().body(customExceptionStr);
		}
		
		 if(temp.getId()!=null){
			 if(Products.getDescription()!=null)
				 temp.setDescription(Products.getDescription());
			 if(Products.getIsAvailable()!=null)
				 temp.setIsAvailable(Products.getIsAvailable());
			 if(Products.getName()!=null)
				 temp.setName(Products.getName());
			 if(Products.getSubCategoryId()!=null)
			 {
				 Categories categories=CategoriesRepository.findById(Products.getSubCategoryId());
					if(categories==null)
					{
						CustomParameterizedException customException=new CustomParameterizedException("Category Not Available", "500");
			            ObjectMapper Obj = new ObjectMapper(); 
						 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
				 		return ResponseEntity.ok().body(customExceptionStr);
					}
				 temp.setSubCategoryId(Products.getSubCategoryId());
			 }
			 if(Products.getCost()!=null)
				 temp.setCost(Products.getCost());
			 if(Products.getWalletCreditAmt()!=null)
				 temp.setWalletCreditAmt(Products.getWalletCreditAmt());
			 if(Products.getImageList()!=null)
			 {
				 if(temp.getProductImage1() != null)
				 temp.setProductImage1(null);
				 
				 if(temp.getProductImage2() != null)
				 temp.setProductImage2(null);
				 
				 if(temp.getProductImage3() != null)
				 temp.setProductImage3(null);
				 
				 if(temp.getProductImage4() != null)
				 temp.setProductImage4(null);
				 
				 for(int j=0;j<Products.getImageList().size();j++)
				 {
					 if(Products.getImageList().get(j)!=null)
					 {
						 if(j==0)
							 temp.setProductImage1(Products.getImageList().get(j));
						 else if(j==1)
							 temp.setProductImage2(Products.getImageList().get(j));
						 else if(j==2)
							 temp.setProductImage3(Products.getImageList().get(j));
						 else if(j==3)
							 temp.setProductImage4(Products.getImageList().get(j));
					 }
				 }
			 }
			 if(Products.getDiscountId()!=null)
			 {
				 Discount discount=DiscountRepository.findById(Products.getDiscountId());
				 if(discount==null)
				 {
						CustomParameterizedException customException=new CustomParameterizedException("Discount not available", "500");
			            ObjectMapper Obj = new ObjectMapper(); 
						 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
				 		return ResponseEntity.ok().body(customExceptionStr);
					}
				 
				 temp.setDiscountId(Products.getDiscountId());
			 }
			 if(Products.getBrandId()!=null)
			 {
				 Categories brand=CategoriesRepository.findById(Products.getBrandId());
					if(brand==null)
					{
						CustomParameterizedException customException=new CustomParameterizedException("Brand Not Available", "500");
			            ObjectMapper Obj = new ObjectMapper(); 
						 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
				 		return ResponseEntity.ok().body(customExceptionStr);
					}
				 temp.setBrandId(Products.getBrandId());
			 }
			 
			 if(Products.getTypeId()!=null)
			 {
				 ProductType type=  ProductTypeRepository.findById(Products.getTypeId());
					if(type==null)
					{
						CustomParameterizedException customException=new CustomParameterizedException("Product Type Not Available", "500");
			            ObjectMapper Obj = new ObjectMapper(); 
						 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
				 		return ResponseEntity.ok().body(customExceptionStr);
					}
				 temp.setTypeId(Products.getTypeId());
			 }
			 
			 
			 Products result = productsRepository.save(temp);
			 if(result.getBrandId()!=null)
			 {
				 Categories brand=CategoriesRepository.findById(result.getBrandId());
				 result.setBrand(brand);
			 }
			 if(result.getSubCategoryId()!=null)
			 {
				 Categories categories=CategoriesRepository.findById(result.getSubCategoryId());
				 result.setCategory(categories);
			 }
			 if(Products.getDiscountId()!=null)
			 {
				Discount  discount= DiscountRepository.findById(result.getDiscountId());
				Long discountPercent=discount.getPercentageOfBaseCost();
				Long productCost=result.getCost();
				double d1=(double)discountPercent;
				double price=productCost*(d1/100);
				Long lPrice=(long)price;
				result.setCostAfterDiscount(result.getCost()-lPrice);
				 result.setDiscount(discount);
			 }
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Products updated successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(result);
	         ObjectMapper Obj = new ObjectMapper(); 
		     String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }else{
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Products not updated successfully");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }
		 
	}

	@GetMapping("/Products")
	public ResponseEntity<String> getAllProducts(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
		if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);
		List<Products> obj= productsRepository.findAll();
		for(Products product:obj) {
			
			if(product.getTypeId()!=null) {
				List<ProductType> typeList = new ArrayList<ProductType>();
				typeList.add(ProductTypeRepository.findOne(product.getTypeId()));
				product.setProductTypeObj(typeList);

			}
			 Categories brand=CategoriesRepository.findById(product.getBrandId());
			 if(brand!=null)
				 product.setBrand(brand);
			 Categories category =CategoriesRepository.findById(product.getSubCategoryId());
			 if(category!=null)
				 product.setCategory(category);
			 Discount discount=DiscountRepository.findById(product.getDiscountId());
			 if(discount!=null)
			 {
				 Long discountPercent=discount.getPercentageOfBaseCost();
					Long productCost=product.getCost();
					double d1=(double)discountPercent;
					double price=productCost*(d1/100);
					Long lPrice=(long)price;
					product.setCostAfterDiscount(product.getCost()-lPrice);
				 product.setDiscount(discount);
			 }
		}
		
		Page<Products> result= new PageImpl<Products>(obj, pageable, obj.size());
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Got all products succesfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
        ObjectMapper Obj = new ObjectMapper(); 
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	}
	
	@GetMapping("/Products/newProducts")
	public ResponseEntity<String> getNewProducts() throws JsonProcessingException {
		
		
		List<Products> obj= productsRepository.findNewProducts();
		if(obj.size()!=0)
		{
			for(Products product:obj) {
				if(product.getTypeId()!=null) {
				   	//product.setProductTypeObj(ProductTypeRepository.findOne(product.getTypeId()));
					List<ProductType> typeList = new ArrayList<ProductType>();
					typeList.add(ProductTypeRepository.findOne(product.getTypeId()));
					product.setProductTypeObj(typeList);
				}
				 Categories brand=CategoriesRepository.findById(product.getBrandId());
				 if(brand!=null)
					 product.setBrand(brand);
				 Categories category =CategoriesRepository.findById(product.getSubCategoryId());
				 if(category!=null)
					 product.setCategory(category);
				 Discount discount=DiscountRepository.findById(product.getDiscountId());
				 if(discount!=null)
				 {
					 Long discountPercent=discount.getPercentageOfBaseCost();
						Long productCost=product.getCost();
						double d1=(double)discountPercent;
						double price=productCost*(d1/100);
						Long lPrice=(long)price;
						product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
				 }
			}
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
	    jsonobjectFormat.setMessage("Got new products");
		jsonobjectFormat.setSuccess(true);
	    jsonobjectFormat.setData(obj);
	    ObjectMapper Obj = new ObjectMapper(); 
	    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
   	    return ResponseEntity.ok().body(customExceptionStr);
		}else {
		
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("No new product found");
		 jsonobjectFormat.setSuccess(false);
	
        ObjectMapper Obj = new ObjectMapper(); 
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	}
	

	
	@GetMapping("/Products/List")
	public ResponseEntity<String> getAllProductsList() throws JsonProcessingException {
		
		List<Products> obj= productsRepository.findAll();
		if(obj.size()!=0)
		{
			for(Products product:obj) {
				if(product.getTypeId()!=null) {
				   	//product.setProductTypeObj(ProductTypeRepository.findOne(product.getTypeId()));
					List<ProductType> typeList = new ArrayList<ProductType>();
					typeList.add(ProductTypeRepository.findOne(product.getTypeId()));
					product.setProductTypeObj(typeList);
				}
				 Categories brand=CategoriesRepository.findById(product.getBrandId());
				 if(brand!=null)
					 product.setBrand(brand);
				 Categories category =CategoriesRepository.findById(product.getSubCategoryId());
				 if(category!=null)
					 product.setCategory(category);
				 Discount discount=DiscountRepository.findById(product.getDiscountId());
				 if(discount!=null)
				 {
					 Long discountPercent=discount.getPercentageOfBaseCost();
						Long productCost=product.getCost();
						double d1=(double)discountPercent;
						double price=productCost*(d1/100);
						Long lPrice=(long)price;
						product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
				 }
			}
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Got all Product list successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(obj);
         ObjectMapper Obj = new ObjectMapper(); 
	     String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("No product found");
		 jsonobjectFormat.setSuccess(false);
         ObjectMapper Obj = new ObjectMapper(); 
	     String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	 
	}
	@GetMapping("/Products/categoryId")
	public ResponseEntity<String> getAllProductsBasedOnCategoryId(@RequestParam("categoryId") Long categoryId) throws JsonProcessingException {
		
		List<Products> obj= productsRepository.findBySubCategoryId(categoryId);
		if(obj.size()!=0)
		{
			for(Products product:obj) {
				if(product.getTypeId()!=null) {
				   	//product.setProductTypeObj(ProductTypeRepository.findOne(product.getTypeId()));
					List<ProductType> typeList = new ArrayList<ProductType>();
					typeList.add(ProductTypeRepository.findOne(product.getTypeId()));
					product.setProductTypeObj(typeList);
				}
				 Categories brand=CategoriesRepository.findById(product.getBrandId());
				 if(brand!=null)
					 product.setBrand(brand);
				 Categories category =CategoriesRepository.findById(product.getSubCategoryId());
				 if(category!=null)
					 product.setCategory(category);
				 Discount discount=DiscountRepository.findById(product.getDiscountId());
				 if(discount!=null)
				 {
					 Long discountPercent=discount.getPercentageOfBaseCost();
						Long productCost=product.getCost();
						double d1=(double)discountPercent;
						double price=productCost*(d1/100);
						Long lPrice=(long)price;
						product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
				 }
			}
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Got all Product based on Category successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(obj);
         ObjectMapper Obj = new ObjectMapper(); 
	     String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("No Product found");
		 jsonobjectFormat.setSuccess(false);
	
         ObjectMapper Obj = new ObjectMapper(); 
	     String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	 
	}
	
	@GetMapping("/Products/staticId")
	public ResponseEntity<String> getAllProductsBasedOnStaticId(@RequestParam("staticId") Long staticId) throws JsonProcessingException {
		
		List<Products> result=new ArrayList<Products>();
		
		List<Products> subCatList= productsRepository.findBySubCategoryId(staticId);
		List<Products> brandList=productsRepository.findByBrandId(staticId);
		if(subCatList!=null && !subCatList.isEmpty())
		{
			result.addAll(subCatList);
		}else if(brandList!=null && !brandList.isEmpty())
		{
			result.addAll(brandList);
		}
		if(result!=null && !result.isEmpty())
		{
			for(Products product:result) {
				if(product.getTypeId()!=null) {
				   	//product.setProductTypeObj(ProductTypeRepository.findOne(product.getTypeId()));
					List<ProductType> typeList = new ArrayList<ProductType>();
					typeList.add(ProductTypeRepository.findOne(product.getTypeId()));
					product.setProductTypeObj(typeList);
				}
				 Categories brand=CategoriesRepository.findById(product.getBrandId());
				 if(brand!=null)
					 product.setBrand(brand);
				 Categories category =CategoriesRepository.findById(product.getSubCategoryId());
				 if(category!=null)
					 product.setCategory(category);
				 Discount discount=DiscountRepository.findById(product.getDiscountId());
				 if(discount!=null)
				 {
					 Long discountPercent=discount.getPercentageOfBaseCost();
						Long productCost=product.getCost();
						double d1=(double)discountPercent;
						double price=productCost*(d1/100);
						Long lPrice=(long)price;
						product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
				 }
			}
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Got all Product based on Category successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
         ObjectMapper Obj = new ObjectMapper(); 
	     String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("No Product found");
		 jsonobjectFormat.setSuccess(false);
	
         ObjectMapper Obj = new ObjectMapper(); 
	     String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	 
	}
	
	
	
	@GetMapping("/Products/brandId")
	public ResponseEntity<String> getAllProductsBasedOnBrandId(@RequestParam("brandId") Long brandId) throws JsonProcessingException {
		
		List<Products> obj= productsRepository.findByBrandId(brandId);
		if(obj.size()!=0)
		{
			for(Products product:obj) {
				if(product.getTypeId()!=null) {
				   	//product.setProductTypeObj(ProductTypeRepository.findOne(product.getTypeId()));
					List<ProductType> typeList = new ArrayList<ProductType>();
					typeList.add(ProductTypeRepository.findOne(product.getTypeId()));
					product.setProductTypeObj(typeList);
				}
				 Categories brand=CategoriesRepository.findById(product.getBrandId());
				 if(brand!=null)
					 product.setBrand(brand);
				 Categories category =CategoriesRepository.findById(product.getSubCategoryId());
				 if(category!=null)
					 product.setCategory(category);
				 Discount discount=DiscountRepository.findById(product.getDiscountId());
				 if(discount!=null)
				 {
					 Long discountPercent=discount.getPercentageOfBaseCost();
						Long productCost=product.getCost();
						double d1=(double)discountPercent;
						double price=productCost*(d1/100);
						Long lPrice=(long)price;
						product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
				 }
			}
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Got all Product based on Brand successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(obj);
         ObjectMapper Obj = new ObjectMapper(); 
	     String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("No Product found");
		 jsonobjectFormat.setSuccess(false);
         ObjectMapper Obj = new ObjectMapper(); 
	     String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	 
	}
	
	@GetMapping("/Products/Id")
	public ResponseEntity<String> getProductsBasedOnId(@RequestParam(value = "id", required=true)Long id) throws JsonProcessingException {
	
		Products product = productsRepository.findById(id);
		if(product!=null)
		{
			if(product.getTypeId()!=null) {
			   	//product.setProductTypeObj(ProductTypeRepository.findOne(product.getTypeId()));
				List<ProductType> typeList = new ArrayList<ProductType>();
				typeList.add(ProductTypeRepository.findOne(product.getTypeId()));
				product.setProductTypeObj(typeList);
			}
				 Categories brand=CategoriesRepository.findById(product.getBrandId());
				 if(brand!=null)
					 product.setBrand(brand);
				 Categories category =CategoriesRepository.findById(product.getSubCategoryId());
				 if(category!=null)
					 product.setCategory(category);
				 Discount discount=DiscountRepository.findById(product.getDiscountId());
				 if(discount!=null)
				 {
					 Long discountPercent=discount.getPercentageOfBaseCost();
						Long productCost=product.getCost();
						double d1=(double)discountPercent;
						double price=productCost*(d1/100);
						Long lPrice=(long)price;
						product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
				 }
				 
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Got product details successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(product);
	 	 ObjectMapper Obj = new ObjectMapper(); 
	 	 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("No product found");
			 jsonobjectFormat.setSuccess(false);
		 	 ObjectMapper Obj = new ObjectMapper(); 
		 	 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	
	}
	
	
	@GetMapping("/Products/filter")
	public ResponseEntity<String> getFilteredProducts(@RequestParam(value="maxPrice",required = false)Long maxPrice,@RequestParam(value="minPrice",required = false)Long minPrice
			,@RequestParam(value="brandId",required = false)Long brandId,@RequestParam(value="subCategoryId",required = false)Long subCategoryId) throws JsonProcessingException {
	
		List<Products> productList=new ArrayList<>();
		if(maxPrice==null & minPrice==null & subCategoryId==null)
		{
			List<Products> brandProd=productsRepository.findByBrandId(brandId);
			productList.addAll(brandProd);
		}else if(brandId==null && subCategoryId==null)
		{
			List<Products> costProd=productsRepository.findByCost(minPrice, maxPrice);
			productList.addAll(costProd);
		}else if(brandId==null & maxPrice==null & minPrice ==null)
		{
			List<Products> subCategoryProd=productsRepository.findBySubCategoryId(subCategoryId);
			productList.addAll(subCategoryProd);
		}else if(minPrice==null & maxPrice==null)
		{
			List<Products> subCategoryBrandProd=productsRepository.findBySubCategoryIdAndBrandId(subCategoryId,brandId);
			productList.addAll(subCategoryBrandProd);
		}else if(subCategoryId==null)
		{
			List<Products> costBrandProd=productsRepository.findByCostAndBrandId(minPrice, maxPrice,brandId);
			productList.addAll(costBrandProd);
		}else if(brandId==null)
		{
			List<Products> costSubCategoryProd=productsRepository.findByCostAndSubCategoryId(minPrice, maxPrice, subCategoryId);
			productList.addAll(costSubCategoryProd);
		}else {
			List<Products> costSubCategoryBrandProd=productsRepository.findByCostAndSubCategoryIdAndBrandId(minPrice, maxPrice, subCategoryId,brandId);
			productList.addAll(costSubCategoryBrandProd);
		}
		
		
		if(productList!=null && !productList.isEmpty())
		{
			for(Products product:productList)
			{
				 Categories brand=CategoriesRepository.findById(product.getBrandId());
				 if(brand!=null)
					 product.setBrand(brand);
				 Categories category =CategoriesRepository.findById(product.getSubCategoryId());
				 if(category!=null)
					 product.setCategory(category);
				 Discount discount=DiscountRepository.findById(product.getDiscountId());
				 if(discount!=null)
				 {
					 Long discountPercent=discount.getPercentageOfBaseCost();
						Long productCost=product.getCost();
						double d1=(double)discountPercent;
						double price=productCost*(d1/100);
						Long lPrice=(long)price;
						product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
				 }
				 
			}
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Got product details successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(productList);
	 	 ObjectMapper Obj = new ObjectMapper(); 
	 	 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("No product found");
			 jsonobjectFormat.setSuccess(false);
		 	 ObjectMapper Obj = new ObjectMapper(); 
		 	 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	
	}

	
	
	@Transactional
	@DeleteMapping("/Products")
	public ResponseEntity<String> deleteProducts(@RequestParam("id") Long id,HttpServletRequest request) throws JsonProcessingException  {
		log.debug("REST request to delete Products : {}", id);
		try {
			productsRepository.deleteById(id);
		
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Recorded deleted successfully");
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	         return ResponseEntity.ok().body(customExceptionStr);
	        }catch(Exception e){
	        	JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Recorded not deleted successfully");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		         return ResponseEntity.ok().body(customExceptionStr);
	        }
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/UploadProductImage")
	public ResponseEntity<String> uploadProductImage(@RequestParam MultipartFile file) throws JsonProcessingException  {
		log.debug("Uploading file on aws", file.getName());
		try {
			String fileUrl = amazonClient.uploadFile(file);
		
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Image uploaded successfully");
			 jsonobjectFormat.setData(fileUrl);
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	         return ResponseEntity.ok().body(customExceptionStr);
	        }catch(Exception e){
	        	JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Image not uploaded successfully");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		         return ResponseEntity.ok().body(customExceptionStr);
	        }
	}
		
	
}
