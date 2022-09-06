package com.ecom.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.ecom.core.dto.Banner;
import com.ecom.core.dto.Categories;
import com.ecom.core.dto.Products;
import com.ecom.core.exception.CustomParameterizedException;
import com.ecom.core.repository.BannerRepository;
import com.ecom.core.repository.CategoriesRepository;
import com.ecom.core.repository.ProductsRepository;
import com.ecom.core.util.AmazonClient;
import com.ecom.core.util.MimeTypes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value="HomeScreenController", description="HomeScreen Banners") 
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class HomeScreenController {
	
	private final Logger log = LoggerFactory.getLogger(HomeScreenController.class);

	
	@Autowired
	private static MimeTypes mimeType;
	
	@Value("${AWSKey}")
	private static  String awsKey;
	
	@Value("${AWSValue}")
	private static String awsValue;
	
	private static String BUCKET_NAME = "ecommerce";
	
	@Autowired
	BannerRepository BannerRepository;
	
	@Autowired
	CategoriesRepository CategoriesRepository;
	
	@Autowired
	ProductsRepository productRepository;
	
	@Autowired
	AmazonClient amazonClient;
	
	
	@PostMapping("/home")
	public ResponseEntity<String> saveBanners(@RequestBody Banner banner) throws JsonProcessingException {
		log.debug("REST request to save Banners : {}", banner);
	
		//Categories category=CategoriesRepository.findById(banner.getCategoryId());
		/*
		 * if(category==null) { CustomParameterizedException customException = new
		 * CustomParameterizedException("Category Not Available", "500"); ObjectMapper
		 * Obj = new ObjectMapper(); String customExceptionStr =
		 * Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 * return ResponseEntity.ok().body(customExceptionStr); }
		 */
		Banner ban =BannerRepository.save(banner);

		if (ban!=null) {
			// that means queue already exists
		//	ban.setCategory(category);
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("banners Added successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(ban);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Banners not saved successfully");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}
	
	
	@GetMapping("/home/list")
	public ResponseEntity<String> getHomeScreen() throws JsonProcessingException {
		
		List<Banner> banners=(List<Banner>) BannerRepository.findAll(); 
		if(banners.size()!=0)
		{
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Got Banners  successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(banners);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Banners Not Avaiable");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}
	
	@Transactional
	@DeleteMapping("/home/banner")
	public ResponseEntity<String> deleteBanner(@RequestParam("id") Long id, HttpServletRequest request)
			throws JsonProcessingException {
		log.debug("REST request to delete Banner : {}", id);
		try {
			BannerRepository.deleteById(id);
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

	@GetMapping("/home/listOfProducts")
	public ResponseEntity<String> searchProductsByKeyWord(@RequestParam ("Keyword") String keyWord) throws JsonProcessingException {
		
		if(keyWord != null) {
         System.out.println("Keyword is the:"+keyWord);
		}
		List <Products> productList = productRepository.findByKeyWord(keyWord);
			
			if(productList.size()!=0)
		{
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Got Products successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(productList);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Products Not Avaiable");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}
	

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/Home/UploadBannerImage")
	public ResponseEntity<String> uploadBannerImage(@RequestParam MultipartFile file) throws JsonProcessingException  {
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
