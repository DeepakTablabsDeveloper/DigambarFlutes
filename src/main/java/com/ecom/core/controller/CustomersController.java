package com.ecom.core.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.ecom.core.dto.Customers;
import com.ecom.core.dto.Wallet;
import com.ecom.core.exception.CustomParameterizedException;
import com.ecom.core.repository.CustomersRepository;
import com.ecom.core.repository.WalletReposiory;
import com.ecom.core.util.FireBaseNotifications;
import com.ecom.core.util.SendEmailNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

/*
 * 
 * To start queue and register business in queue
 * 
 */
@RestController
@Api(value="CustomersController", description="Customers details") 
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class CustomersController {
	
	private final Logger log = LoggerFactory.getLogger(CustomersController.class);

	@Autowired
	CustomersRepository userDetailsRepository;

	@Autowired
	FireBaseNotifications fireBaseNotifications;
	
	@Autowired
	SendEmailNotifications sendEmailNotifications;
	
	@Autowired
	 WalletReposiory  WalletReposiory;
	
	@PostMapping("/authLogin/emailId")
	public ResponseEntity<String> checkAuthUsingEmailId(@RequestParam("emailId")String emailId,@RequestParam("password")String password,@RequestParam("os")String os,@RequestParam("deviceId")String deviceId,@PageableDefault(page=0,size = Integer.MAX_VALUE) Pageable pageable) throws JsonProcessingException{
		
		List <Customers>  userDetailsList=userDetailsRepository.findByEmailIdAndPassword(emailId, password);
		try{
			Customers userDetailsObj=userDetailsList.get(0);
			if(userDetailsObj.getId()!=null){
				userDetailsObj.setDeviceId(deviceId);
				Customers details=userDetailsRepository.save(userDetailsObj);
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("User Authentication successfull");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(details);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}else{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("User Authentication not successfull");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		}catch(IndexOutOfBoundsException e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("User Authentication not successfull");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
		
	}
	
	@PostMapping("/authLogin/mobileNumber")
	public ResponseEntity<String> checkAuthUsingMobileNumber(@RequestParam("mobileNumber")String mobileNumber,@RequestParam("password")String password,@RequestParam("os")String os,@RequestParam("deviceId")String deviceId,@PageableDefault(page=0,size = Integer.MAX_VALUE) Pageable pageable,@RequestParam(value="isGoogleLogin",required=false) boolean isGoogleLogin) throws JsonProcessingException{
		List <Customers>  userDetailsList=userDetailsRepository.findByMobileNumberAndPassword(mobileNumber, password);
		try{
			Customers userDetailsObj=userDetailsList.get(0);
			if(userDetailsObj.getId()!=null){
				userDetailsObj.setDeviceId(deviceId);
				Customers details=userDetailsRepository.save(userDetailsObj);
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("User Authentication successfull");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(details);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}else{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("User Authentication not successfull");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
		}catch(IndexOutOfBoundsException e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("User Authentication not successfull");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	}
	
	
	
	
	@PostMapping("/Customers")
	public ResponseEntity<String> saveCustomers(@RequestBody Customers Customers) throws JsonProcessingException {
		log.debug("REST request to save Customer Details : {}", Customers);
		Customers customerEmailAndMobileValidation=userDetailsRepository.findByMobileNumberAndEmailId(Customers.getMobileNumber(),Customers.getEmailId());
		if(customerEmailAndMobileValidation!=null)
		{
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("User is Already registered from this mobileNumber and emailId");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		     return ResponseEntity.ok().body(customExceptionStr);
		}
		Customers mobileNumberValidation=userDetailsRepository.findByMobileNumber(Customers.getMobileNumber());
		if(mobileNumberValidation!=null)
		{
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("MobileNumber Already Registered ");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		     return ResponseEntity.ok().body(customExceptionStr);
		}
		Customers emailValidation=userDetailsRepository.findByEmailId(Customers.getEmailId());
		if(emailValidation!=null)
		{
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("emailId Already Registered ");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		     return ResponseEntity.ok().body(customExceptionStr);
		}
			 
		 
		 try{
			
			 Customers userDetails= userDetailsRepository.save(Customers);
			 
			 Wallet wallet=new Wallet(userDetails.getId(),BigDecimal.ZERO);
			 WalletReposiory.save(wallet);
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("User Regsitered successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(userDetails);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }catch (Exception e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Unable to Register User");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		     return ResponseEntity.ok().body(customExceptionStr);
		 }
		 
		
	}
	
	@PostMapping("/Customers/guest")
	public ResponseEntity<String> saveCustomerAsGuest(@RequestBody Customers Customers) throws JsonProcessingException {
		log.debug("REST request to save Customer Details : {}", Customers);
		
		Customers mobileNumberValidation=userDetailsRepository.findByMobileNumber(Customers.getMobileNumber());
		if(mobileNumberValidation!=null)
		{
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("MobileNumber Already Registered ");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		     return ResponseEntity.ok().body(customExceptionStr);
		}
		 
		 try{
			 Customers.setIsGuest("true");
			 Customers userDetails= userDetailsRepository.save(Customers);
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("User Regsitered as Guest successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(userDetails);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 }catch (Exception e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Unable to Register User as Guest");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		     return ResponseEntity.ok().body(customExceptionStr);
		 }
		 
		
	}

	@PutMapping("/Customers")
	public ResponseEntity<String> updateCustomers(@RequestBody Customers Customers) throws JsonProcessingException  {
		log.debug("REST request to update Customers : {}", Customers);
		Customers temp=userDetailsRepository.findById(Customers.getId());
		if (Customers.getId()==null||temp==null) {
			 CustomParameterizedException customException=new CustomParameterizedException("Invalid Id", "500");
	            ObjectMapper Obj = new ObjectMapper(); 
				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 		return ResponseEntity.ok().body(customExceptionStr);
		}
		
		if(Customers.getAddress()!=null)
			temp.setAddress(Customers.getAddress());
		if(Customers.getCity()!=null)
			temp.setCity(Customers.getCity());
		if(Customers.getCountry()!=null)
			temp.setCountry(Customers.getCountry());
		if(Customers.getEmailId()!=null)
			temp.setEmailId(Customers.getEmailId());
		if(Customers.getLandmark()!=null)
			temp.setLandmark(Customers.getLandmark());
		if(Customers.getLastName()!=null)
			temp.setLastName(Customers.getLastName());
		if(Customers.getMobileNumber()!=null)
			temp.setMobileNumber(Customers.getMobileNumber());
		if(Customers.getName()!=null)
			temp.setName(Customers.getName());
		if(Customers.getPinCode()!=null)
			temp.setPinCode(Customers.getPinCode());
		if(Customers.getState()!=null)
			temp.setState(Customers.getState());
		
		
		 Customers userDetails= userDetailsRepository.save(temp);
		 if(userDetails.getId()!=null){
			 //that means queue already exists
			 
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("User details updated successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(userDetails);
	            ObjectMapper Obj = new ObjectMapper(); 
				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 		return ResponseEntity.ok().body(customExceptionStr);
		 }else{
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("User details not updated successfully");
			 jsonobjectFormat.setSuccess(false);

	            ObjectMapper Obj = new ObjectMapper(); 
				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 		return ResponseEntity.ok().body(customExceptionStr);
		 }
		 
	}

	@GetMapping("/Customers")
	public ResponseEntity<String> getAllCustomers(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=10;
		 }
		if(page!=null){
		 }else{
			 page=0;
		 }
		Pageable pageable =new PageRequest(page, size);
		List<Customers> obj= userDetailsRepository.findAll();	
		if(obj.size()!=0)
		{
			Page<Customers> result=new PageImpl<>(obj, pageable, obj.size());
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Got All User Details  successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(result);
        ObjectMapper Obj = new ObjectMapper(); 
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Users Not Found");
			 jsonobjectFormat.setSuccess(false);
			
	            ObjectMapper Obj = new ObjectMapper(); 
				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 		return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	
	
	@GetMapping("/Customers/List")
	public ResponseEntity<String> getAllCustomersList() throws JsonProcessingException {
		
	
		List<Customers> data= userDetailsRepository.findAll();
		if(data.size()!=0)
		{
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Got all Users successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(data);
            ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 		return ResponseEntity.ok().body(customExceptionStr);
		}else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Users Not Found");
			 jsonobjectFormat.setSuccess(false);
			
	            ObjectMapper Obj = new ObjectMapper(); 
				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 		return ResponseEntity.ok().body(customExceptionStr);
		}
	 
	}
	
	@GetMapping("/Customers/Guest/List")
	public ResponseEntity<String> getAllGuestCustomersList() throws JsonProcessingException {
		
		List<Customers> data= userDetailsRepository.findAllGuest();
		if(data.size()!=0)
		{
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Got all Guest Users successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(data);
            ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 		return ResponseEntity.ok().body(customExceptionStr);
		}else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Guest Users Not Found");
			 jsonobjectFormat.setSuccess(false);
			
	            ObjectMapper Obj = new ObjectMapper(); 
				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 		return ResponseEntity.ok().body(customExceptionStr);
		}
	 
	}
	
	
	@GetMapping("/Customers/Id")
	public ResponseEntity<String> getCustomer(@RequestParam(value = "id", required=true)Long id) throws JsonProcessingException {
		
		
		log.debug("REST request to get User Details : {}", id);
		Customers Customers = userDetailsRepository.findById(id);
		if(Customers!=null)
		{
		JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("User details got perfectly");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(Customers);
       ObjectMapper Obj = new ObjectMapper(); 
       String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("User Not Found");
			 jsonobjectFormat.setSuccess(false);
	            ObjectMapper Obj = new ObjectMapper(); 
				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 		return ResponseEntity.ok().body(customExceptionStr);
		}
	}
	
	@Transactional
	@DeleteMapping("/Customers")
	public ResponseEntity<String> deleteCustomers(@RequestParam("id") Long id,HttpServletRequest request) throws JsonProcessingException  {
		log.debug("REST request to delete Customer : {}", id);
		try {
			userDetailsRepository.deleteById(id);;
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
	
	 @PostMapping("SendEmail")
	 public ResponseEntity<String> sendOtpToEmail(@RequestParam("emailId")String emailId) throws JsonProcessingException{
		try{
			
			String random=getRandomNumberString();
			Customers customer=userDetailsRepository.findByEmailId(emailId);
			customer.setOtp(random);
			userDetailsRepository.save(customer);
			
			String subject="Otp";
			sendEmailNotifications.sendSimpleMessage(emailId,subject,random);
	 		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Email sent successfully");
			 jsonobjectFormat.setSuccess(true);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		 	 
		}catch(Exception e){
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Something went wrong unable to send verification mail");
			 jsonobjectFormat.setSuccess(false);
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
	 }
	 
	 
	 @PostMapping("verifyOtp")
	 public ResponseEntity<String> verifyOtp(@RequestParam("otp") String otp,@RequestParam("emailId")String emailId) throws JsonProcessingException{
		
			 if(otp!=null)
			 {
				Customers customer=userDetailsRepository.findByEmailId(emailId); 
				if(otp.equals(customer.getOtp()))
				{
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 jsonobjectFormat.setMessage("verify OTP sucess");
					 jsonobjectFormat.setSuccess(true);
			         ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
				}
			else{
					 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					 jsonobjectFormat.setMessage("wrong otp");
					 jsonobjectFormat.setSuccess(false);
			         ObjectMapper Obj = new ObjectMapper(); 
			         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				 	 return ResponseEntity.ok().body(customExceptionStr);
				}
			 }
			 else{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("otp cannot be null");
				 jsonobjectFormat.setSuccess(false);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			}
	
		
		 
	 }
	 
	 @PutMapping("/UpdateStatus")
		public ResponseEntity<String> updateStatusCustomer(@RequestParam(value = "id")Long id, @RequestParam(value = "isActive")String isActive ) throws JsonProcessingException  {
			log.debug("REST request to update status of Customer with id : {}", id);
			Customers tempCostumer =userDetailsRepository.findById(id);
			if (id ==null||tempCostumer==null) {
				 CustomParameterizedException customException=new CustomParameterizedException("Invalid Id", "500");
		            ObjectMapper Obj = new ObjectMapper(); 
					 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			 		return ResponseEntity.ok().body(customExceptionStr);
			}
			
			if(isActive!=null)
				tempCostumer.setIsActive(isActive);
			
			 Customers userDetails= userDetailsRepository.save(tempCostumer);
			 if(userDetails.getId()!=null){
				 //that means queue already exists
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("User status updated successfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(userDetails);
		            ObjectMapper Obj = new ObjectMapper(); 
					 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 		return ResponseEntity.ok().body(customExceptionStr);
			 }else{
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("User status not updated successfully");
				 jsonobjectFormat.setSuccess(false);

		            ObjectMapper Obj = new ObjectMapper(); 
					 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 		return ResponseEntity.ok().body(customExceptionStr);
			 }
			 
		}
	 
	 @PostMapping("/resetPassword")
	 public ResponseEntity<String> resetPassword(@RequestParam("emailId")String emailId,@RequestParam("password") String newPassword) throws JsonProcessingException{
		

		 if(emailId!=null){
			 Customers userMaster=userDetailsRepository.findByEmailId(emailId);
			 if(userMaster.getId()!=null){
				 //user found and using email Id to send notifications
				 userMaster.setPassword(newPassword);
				 userDetailsRepository.save(userMaster);
				 
				 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
				 jsonobjectFormat.setMessage("reset password process initiated succcessfully");
				 jsonobjectFormat.setSuccess(true);
				 jsonobjectFormat.setData(userMaster);
		         ObjectMapper Obj = new ObjectMapper(); 
		         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			 	 return ResponseEntity.ok().body(customExceptionStr);
			 }
		 }
		 
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Please enter valid credentials to reset password...");
		 jsonobjectFormat.setSuccess(false);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);

	 }
	 
	 public static String getRandomNumberString() {
		    // It will generate 4 digit random Number.
		    // from 0 to 9999
		    Random rnd = new Random();
		    int number = rnd.nextInt(9999);

		    // this will convert any number sequence into 6 character.
		    return String.format("%04d", number);
		}
}

