package com.ecom.core.controller;

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

import com.ecom.core.domain.JsonObjectFormat;
import com.ecom.core.dto.Cart;
import com.ecom.core.dto.Categories;
import com.ecom.core.dto.Customers;
import com.ecom.core.dto.Discount;
import com.ecom.core.dto.Products;
import com.ecom.core.exception.CustomParameterizedException;
import com.ecom.core.repository.CartRepository;
import com.ecom.core.repository.CategoriesRepository;
import com.ecom.core.repository.CustomersRepository;
import com.ecom.core.repository.DiscountRepository;
import com.ecom.core.repository.ProductsRepository;
import com.ecom.core.util.CommonEmailService;
import com.ecom.core.util.FireBaseNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "CartController", description = "use to add or remove products from cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class CartController {
	
	private final Logger log = LoggerFactory.getLogger(CartController.class);

	@Autowired
	CartRepository cartDetailsRepository;

	@Autowired
	FireBaseNotifications fireBaseNotifications;

	@Autowired
	CustomersRepository CustomersRepository;
	
	@Autowired
	ProductsRepository ProductsRepository;
	
	@Autowired
	CategoriesRepository CategoriesRepository;
	
	@Autowired
	DiscountRepository DiscountRepository;
	
	

	@PostMapping("/Cart")
	public ResponseEntity<String> saveCart(@RequestBody Cart cart) throws JsonProcessingException {
		log.debug("REST request to save Cart Details : {}", cart);
		try {
			
			Customers customer=CustomersRepository.findById(cart.getCustomerId());
			if(cart.getCustomerId() == 0 && customer==null)
			{
				CustomParameterizedException customException = new CustomParameterizedException("Invalid User", "500");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
				return ResponseEntity.ok().body(customExceptionStr);
			}
			Products product=ProductsRepository.findById(cart.getProductId());
			
			if(cart.getProductId()==0 && product==null)
			{
				CustomParameterizedException customException = new CustomParameterizedException("Invalid Product", "500");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
				return ResponseEntity.ok().body(customExceptionStr);
			}
			
			
				Cart carDetails = cartDetailsRepository.save(cart);
	
				Categories brand=CategoriesRepository.findById(product.getBrandId());
				if(brand!=null) {
				product.setBrand(brand);
				}
				
				Categories categories=CategoriesRepository.findById(product.getSubCategoryId());
				if(categories!=null) {
				 product.setCategory(categories);
				}
				
				 if(product.getDiscountId()!=null && product.getDiscountId()!=0)
				 {
					Discount  discount= DiscountRepository.findById(product.getDiscountId());
					Long discountPercent=discount.getPercentageOfBaseCost();
					Long productCost=product.getCost();
					double d1=(double)discountPercent;
					double price=productCost*(d1/100);
					Long lPrice=(long)price;
					product.setCostAfterDiscount(productCost-lPrice);
					 product.setDiscount(discount);
				 }
				 carDetails.setProduct(product);
				 if(product.getDiscount()!=null)
					{
					 carDetails.setCost(product.getCostAfterDiscount()*carDetails.getQuantity());	
					}else {
						carDetails.setCost(product.getCost()*carDetails.getQuantity());
					}
				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
				jsonobjectFormat.setMessage("Cart saved successfully");
				jsonobjectFormat.setSuccess(true);
				jsonobjectFormat.setData(carDetails);
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				
				return ResponseEntity.ok().body(customExceptionStr);
		} catch(Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Cart not saved successfully");
			jsonobjectFormat.setSuccess(false);
			
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@PutMapping("/Cart")
	public ResponseEntity<String> updateCart(@RequestBody Cart cart) throws JsonProcessingException {
		log.debug("REST request to update Cart : {}", cart);
		Cart temp=cartDetailsRepository.findById(cart.getId());
		if (cart.getId()==null||temp== null) {
			CustomParameterizedException customException = new CustomParameterizedException("Invalid Id", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}
			if(cart.getQuantity()!=null)
				temp.setQuantity(cart.getQuantity());
			if(cart.getCost()!=null)
				temp.setCost(cart.getCost());
		
			try{
			Cart cartDetails = cartDetailsRepository.save(temp);
			Products product=ProductsRepository.findById(cartDetails.getProductId());
			Categories brand=CategoriesRepository.findById(product.getBrandId());
			product.setBrand(brand);
			Categories categories=CategoriesRepository.findById(product.getSubCategoryId());
			 product.setCategory(categories);
			 if(product.getDiscountId()!=null)
			 {
				Discount  discount= DiscountRepository.findById(product.getDiscountId());
				Long discountPercent=discount.getPercentageOfBaseCost();
				Long productCost=product.getCost();
				double d1=(double)discountPercent;
				double price=productCost*(d1/100);
				Long lPrice=(long)price;
				product.setCostAfterDiscount(product.getCost()-lPrice);
				 product.setDiscount(discount);
			 }
			 cartDetails.setProduct(product);
			 if(product.getDiscount()!=null)
				{
				 cartDetails.setCost(product.getCostAfterDiscount()*cartDetails.getQuantity());	
				}else {
					cartDetails.setCost(product.getCost()*cartDetails.getQuantity());
				}
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Cart updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(cartDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} catch(Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Cart not updated successfully");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}

	@GetMapping("Cart")
	public ResponseEntity<String> getAllCart(@RequestParam(value = "page", required = false) Integer page,
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
		List<Cart> listOfCart = cartDetailsRepository.findAll();
		if(!listOfCart.isEmpty())
		{	
			for(Cart cart:listOfCart)
			{
				
				
				Products product=ProductsRepository.findById(cart.getProductId());
				if(product != null && product.getBrandId() != null && product.getBrandId() != 0) 
				{
				Categories brand=CategoriesRepository.findById(product.getBrandId());
				product.setBrand(brand);
				Categories categories=CategoriesRepository.findById(product.getSubCategoryId());
				 product.setCategory(categories);
				 if(product.getDiscountId()!=null && product.getDiscountId() != 0)
				 {
					Discount  discount= DiscountRepository.findById(product.getDiscountId());
					Long discountPercent=discount.getPercentageOfBaseCost();
					Long productCost=product.getCost();
					double d1=(double)discountPercent;
					double price=productCost*(d1/100);
					Long lPrice=(long)price;
					product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
					
				 }
				cart.setProduct(product);
				if(product.getDiscount()!=null && product.getDiscount().getId()!=0)
				{
				 cart.setCost(product.getCostAfterDiscount()*cart.getQuantity());	
				}else {
					cart.setCost(product.getCost()*cart.getQuantity());
				}
			}							
		}	
			if(listOfCart.isEmpty()) {

				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
				jsonobjectFormat.setMessage("Cart not found");
				jsonobjectFormat.setSuccess(false);
			
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
				
			}
			Page<Cart> result=new PageImpl<>(listOfCart, pageable, listOfCart.size());
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Got all Carts successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(result);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}else {
	
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Cart not found");
		jsonobjectFormat.setSuccess(false);
	
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}

	@GetMapping("/Cart/List")
	public ResponseEntity<String> getAllCartList() throws JsonProcessingException {
		List<Cart> listOfCart= cartDetailsRepository.findAll();
		if(listOfCart.size()!=0)
		{
			for(Cart cart:listOfCart)
			{
				Products product=ProductsRepository.findById(cart.getProductId());
				Categories brand=CategoriesRepository.findById(product.getBrandId());
				product.setBrand(brand);
				Categories categories=CategoriesRepository.findById(product.getSubCategoryId());
				 product.setCategory(categories);
				 if(product.getDiscountId()!=null)
				 {
					Discount  discount= DiscountRepository.findById(product.getDiscountId());
					Long discountPercent=discount.getPercentageOfBaseCost();
					Long productCost=product.getCost();
					double d1=(double)discountPercent;
					double price=productCost*(d1/100);
					Long lPrice=(long)price;
					product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
				 }
				cart.setProduct(product);
				if(product.getDiscount()!=null && product.getDiscount().getId() != 0)
				{
				 cart.setCost(product.getCostAfterDiscount()*cart.getQuantity());	
				}else {
					cart.setCost(product.getCost()*cart.getQuantity());
				}
				
			}
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Got all Cart Details successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(listOfCart);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}else {
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Cart not found");
		jsonobjectFormat.setSuccess(false);
		
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}

	}

	@GetMapping("/Cart/Id/")
	public ResponseEntity<String> getCartProductBasedOnId(@RequestParam(value = "id", required = true) Long id) throws JsonProcessingException {
				
		log.debug("REST request to get cart products : {}", id);
		Cart cart=cartDetailsRepository.findById(id);
		if(cart!=null   && cart.getProductId()!= 0)
		{
			
				Products product=ProductsRepository.findById(cart.getProductId());
				 if(product != null && product.getBrandId()!= null && product.getBrandId()!= 0) {
				Categories brand=CategoriesRepository.findById(product.getBrandId());
				product.setBrand(brand);
				Categories categories=CategoriesRepository.findById(product.getSubCategoryId());
				 product.setCategory(categories);
				 }
				 if(product.getDiscountId()!=null && product.getDiscountId()!= 0)
				 {
					Discount  discount= DiscountRepository.findById(product.getDiscountId());
					Long discountPercent=discount.getPercentageOfBaseCost();
					Long productCost=product.getCost();
					double d1=(double)discountPercent;
					double price=productCost*(d1/100);
					Long lPrice=(long)price;
					product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
				 }
				cart.setProduct(product);
				if(product.getDiscount()!=null && product.getDiscountId() !=0)
				{
				 cart.setCost(product.getCostAfterDiscount()*cart.getQuantity());	
				}else {
					cart.setCost(product.getCost()*cart.getQuantity());
				}
				
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Got cart successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(cart);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Cart not found");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		
		
	}

	@GetMapping("/Cart/GetCartForUser")
	public  ResponseEntity<String> getCartForUser(@RequestParam(value = "customerId", required = true) Long customerId) throws JsonProcessingException {
		
		List<Cart> listOfCart = cartDetailsRepository.findByCustomerId(customerId);
		if(listOfCart.size()!=0)
		{
			for(Cart cart:listOfCart)
			{
				Products product=ProductsRepository.findById(cart.getProductId());
				Categories brand=CategoriesRepository.findById(product.getBrandId());
				product.setBrand(brand);
				Categories categories=CategoriesRepository.findById(product.getSubCategoryId());
				 product.setCategory(categories);
				 if(product.getDiscountId()!=null)
				 {
					Discount  discount= DiscountRepository.findById(product.getDiscountId());
					Long discountPercent=discount.getPercentageOfBaseCost();
					Long productCost=product.getCost();
					double d1=(double)discountPercent;
					double price=productCost*(d1/100);
					Long lPrice=(long)price;
					product.setCostAfterDiscount(product.getCost()-lPrice);
					 product.setDiscount(discount);
				 }
				cart.setProduct(product);
				if(product.getDiscount()!=null)
				{
				 cart.setCost(product.getCostAfterDiscount()*cart.getQuantity());	
				}else {
					cart.setCost(product.getCost()*cart.getQuantity());
				}
				
			}
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got cart For user successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(listOfCart);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}else {
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("cart is empty");
		jsonobjectFormat.setSuccess(false);
	
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	
			
	@DeleteMapping("/Cart/RemoveAllProductsFromCartForUser") 
	public ResponseEntity<String> deleteProductsFromCartForUser(@RequestParam(value = "customerId", required = true) Long customerId) throws JsonProcessingException {
		
		try {
			Customers customer= CustomersRepository.findById(customerId);
			 if(customer != null) {
		
			
			cartDetailsRepository.deleteByCustomerId(customerId);
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record deleted successfully");
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	         return ResponseEntity.ok().body(customExceptionStr);
			 } else {
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Record does not exist");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		         return ResponseEntity.ok().body(customExceptionStr);
			 }
	      
	        }catch(Exception e){
	        	JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Record not deleted successfully");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		         return ResponseEntity.ok().body(customExceptionStr);
	        }
		
	}

	@Transactional
	@DeleteMapping("/Cart")
	public ResponseEntity<String> deleteParticularProductFromCart(@RequestParam("id") Long id,HttpServletRequest request) throws JsonProcessingException  {
		log.debug("REST request to delete Customers : {}", id);
		try {
			
			cartDetailsRepository.deleteById(id);
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Record deleted successfully");
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	         return ResponseEntity.ok().body(customExceptionStr);
	        }catch(Exception e){
	        	JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("Record not deleted successfully");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		         return ResponseEntity.ok().body(customExceptionStr);
	        }
	}
	
	
	@GetMapping("/checkEmail")
	public void testEmail() {
		CommonEmailService.sendEmail(null, null, null);
		System.out.println("Completed");
	}

}
