package com.ecom.core.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.core.domain.JsonObjectFormat;
import com.ecom.core.dto.BaughtProducts;
import com.ecom.core.dto.Cart;
import com.ecom.core.dto.Orders;
import com.ecom.core.dto.Products;
import com.ecom.core.dto.RazorPayObj;
import com.ecom.core.dto.Transactions;
import com.ecom.core.dto.Wallet;
import com.ecom.core.repository.BaughtPruductsRepository;
import com.ecom.core.repository.CartRepository;
import com.ecom.core.repository.CustomersRepository;
import com.ecom.core.repository.OrderRepository;
import com.ecom.core.repository.ProductsRepository;
import com.ecom.core.repository.TransactionsRepository;
import com.ecom.core.repository.WalletReposiory;
import com.ecom.core.util.FireBaseNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import io.swagger.annotations.Api;

@RestController
@Api(value = "TransactionController", description = "used for processing and viewing transactions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class TransactionsController {
	
	private final Logger log = LoggerFactory.getLogger(TransactionsController.class);
	
	@Autowired
	private  TransactionsRepository transactionsRepository;
	
	@Autowired
	CustomersRepository UsersRepository;
	
	@Autowired
	OrderRepository OrderRepository;
	
	@Autowired
	ProductsRepository ProductsRepository;
	
	@Autowired
	BaughtPruductsRepository baughtProductRepository;
	
	@Autowired
	WalletReposiory WalletReposiory;
	
	@Autowired
	CartRepository CartRepository;
	
	@Autowired
	FireBaseNotifications FireBaseNotifications;
	
	@Value("${RazorpayKeyId}")
	private String razorPayKeyId;
	
	@Value("${RazorpayKeySecret}")
	private  String razorPaySecretKey;
	
	
	@PostMapping("/transaction/initiatePayment")
	public ResponseEntity<String> initiatePayement(@RequestParam("amount")Long amount,@RequestParam("currency")String currency) throws JsonProcessingException {
		try {
		RazorpayClient razorpay = new RazorpayClient(razorPayKeyId,razorPaySecretKey); 
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount",amount); // amount in the smallest currency unit
		orderRequest.put("currency", currency);
		String receipt="";
		StringBuilder od=new StringBuilder("OD");	
		receipt=od.append(getRandomNumberString()).toString();
		
		orderRequest.put("receipt", receipt);
		Order order = razorpay.Orders.create(orderRequest);
		String id=order.get("id");
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("transaction initiated  successfully.");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(id);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }catch(Exception e) {
	 		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Transaction unsuccessful.");
			 jsonobjectFormat.setSuccess(false);
			
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }
  	 
	}
	
	@Transactional
	@PostMapping("/transaction/finalPayment")
	public ResponseEntity<String> finalPayment(@RequestBody RazorPayObj razorPayObject) throws JsonProcessingException {
		
		try {
				String generated_signature = hmac_sha256(razorPayObject.getRazorPayOrderId() + "|" + razorPayObject.getTransactionId(),razorPaySecretKey); 
				if (generated_signature.equals(razorPayObject.getSignature()) ) {
					 Date loggedTime=new Date();
					 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
					 String strDate = dateFormat.format(loggedTime);  						
					 DateFormat timeFormat=new SimpleDateFormat("hh:mm:ss");
					 String time =timeFormat.format(loggedTime);
					 
						Orders order=  razorPayObject.getOrder();		
						order.setOrderDate(strDate);
					
						order.setOrderId(razorPayObject.getRazorPayOrderId());
						Orders finalOrder=OrderRepository.save(order);	
						if(finalOrder!=null)
						{
								List<BaughtProducts> list=order.getBaughtProducts();
								List<BaughtProducts> outList=new ArrayList<BaughtProducts>();
								Long walletAddAmount=new Long(0);
								for(BaughtProducts baughtProducts:list)
								{
									baughtProducts.setOrderId(order.getId());
									BaughtProducts temp=baughtProductRepository.save(baughtProducts);
									Cart cart=CartRepository.findByCustomerIdAndProductId(order.getCustomerId(), temp.getProductId());
									if(cart!=null)
										CartRepository.deleteByCustomerIdAndProductId(order.getCustomerId(), temp.getProductId());
									
									Products prod=ProductsRepository.findById(temp.getProductId());
									if(prod.getWalletCreditAmt()!=null)
									{	
										
										walletAddAmount=walletAddAmount+prod.getWalletCreditAmt();
									}
									
									temp.setProduct(prod);
									outList.add(temp);
								}
								order.setBaughtProducts(outList);
								Wallet wallet=	WalletReposiory.findByCustomerId(order.getCustomerId());
								
								if(wallet!=null)
								{
									if(order.getWalletAmtUsed()!=null & wallet.getAmount()!=null  && order.getWalletAmtUsed()!=0  )
										wallet.setAmount(wallet.getAmount().subtract(BigDecimal.valueOf(order.getWalletAmtUsed())));
									
									if(walletAddAmount!=null & walletAddAmount!=0)
										wallet.setAmount(wallet.getAmount().add(BigDecimal.valueOf(walletAddAmount)));
									
									wallet=WalletReposiory.save(wallet);
								
								}else if(wallet==null & walletAddAmount!=null && walletAddAmount!=0)
								{
									wallet=new Wallet(order.getCustomerId(),BigDecimal.valueOf(order.getWalletAmtUsed()));	
									wallet=WalletReposiory.save(wallet);								}
						}
						
					   Transactions transactions=new Transactions(order.getAmtPaid(),razorPayObject.getSignature(), time, finalOrder.getId(), razorPayObject.getRazorPayOrderId(), "successfull", strDate, razorPayObject.getTransactionId(), "online", finalOrder.getCustomerId());
					   transactionsRepository.save(transactions);
					   JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					   jsonobjectFormat.setMessage("transaction  successfull");
					   jsonobjectFormat.setSuccess(true);
					   jsonobjectFormat.setData(finalOrder);
				       ObjectMapper Obj = new ObjectMapper(); 
				       String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				       return ResponseEntity.ok().body(customExceptionStr);
				 }else {
						JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
					    jsonobjectFormat.setMessage("Transaction unsuccessful");
					    jsonobjectFormat.setSuccess(false);
							
					    ObjectMapper Obj = new ObjectMapper(); 
					    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
						return ResponseEntity.ok().body(customExceptionStr);
				 }
	 	 }catch(Exception e) {
	 		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("Transaction unsuccessful");
			 jsonobjectFormat.setSuccess(false);
			
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
	 	 }
  	 
	}
	
	@GetMapping("/Transactions")
	public ResponseEntity<String> getAllTransactions(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=20;
		 }
		if(page!=null){
		 }else{
			 page=0;
		 }
   	 
		Pageable pageable =new PageRequest(page, size);
		log.debug("REST request to get all Transactions");
		List<Transactions> listOfTransactions=new ArrayList<Transactions>();
		
			listOfTransactions= (List<Transactions>) transactionsRepository.findAll();
		
		
		 int newDataStart=pageable.getPageNumber()*size;
		 int end=newDataStart+pageable.getPageSize();
		 if(newDataStart >= listOfTransactions.size()){
			 newDataStart=listOfTransactions.size();
		 }
		 if(end >= listOfTransactions.size()){
			 end=listOfTransactions.size();
		 }
		 Page<Transactions> pages = new PageImpl<Transactions>(listOfTransactions.subList(newDataStart, end), pageable, listOfTransactions.size());
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Transactions fetched successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(pages);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		
	}
	
	
	@GetMapping("/wallet/customerId")
	public ResponseEntity<String> getCustomerWalletBalanace(@RequestParam("customerId")Long customerId) throws JsonProcessingException {
	
   	 
		Wallet wallet=WalletReposiory.findByCustomerId(customerId);
		if(wallet!=null)
		{
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("wallet details fetched successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(wallet);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		}else {
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 jsonobjectFormat.setMessage("unable to fetch wallet details");
			 jsonobjectFormat.setSuccess(false);
			
	         ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	
	
	@GetMapping("/Transactions/customerId")
	public ResponseEntity<String> getAllCustomerTransactions(@RequestParam(value="customerId")Long customerId,@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size) throws JsonProcessingException {
		if(size!=null){
		 }else{
			 size=20;
		 }
		if(page!=null){
		 }else{
			 page=0;
		 }
   	 
		Pageable pageable =new PageRequest(page, size);
		log.debug("REST request to get all Transactions");
		List<Transactions> listOfTransactions=new ArrayList<Transactions>();
		
			listOfTransactions= (List<Transactions>) transactionsRepository.findByUserId(customerId);
		
		
		 int newDataStart=pageable.getPageNumber()*size;
		 int end=newDataStart+pageable.getPageSize();
		 if(newDataStart >= listOfTransactions.size()){
			 newDataStart=listOfTransactions.size();
		 }
		 if(end >= listOfTransactions.size()){
			 end=listOfTransactions.size();
		 }
		 Page<Transactions> pages = new PageImpl<Transactions>(listOfTransactions.subList(newDataStart, end), pageable, listOfTransactions.size());
		 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
		 jsonobjectFormat.setMessage("Transactions fetched successfully");
		 jsonobjectFormat.setSuccess(true);
		 jsonobjectFormat.setData(pages);
         ObjectMapper Obj = new ObjectMapper(); 
         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 	 return ResponseEntity.ok().body(customExceptionStr);
		
	}
	
	
	
	public static String hmac_sha256(String message,String secret)
	{
	    String hash="";
	    try{
	        /*Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
	        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
	        sha256_HMAC.init(secret_key);

	        hash = Base64.encodeToString(sha256_HMAC.doFinal(message.getBytes()));*/
	    	 SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");

	            // get an hmac_sha256 Mac instance and initialize with the signing key
	            Mac mac = Mac.getInstance("HmacSHA256");
	            mac.init(signingKey);

	            // compute the hmac on input data bytes
	            byte[] rawHmac = mac.doFinal(message.getBytes());

	            // base64-encode the hmac
	            hash = DatatypeConverter.printHexBinary(rawHmac).toLowerCase();
	    }catch (Exception e)
	    {

	    }
	    return hash;
	}
	
	public static String getRandomNumberString() {
	    // It will generate 9 digit random Number.
	    // from 0 to 999999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%9d", number);
	}

}
