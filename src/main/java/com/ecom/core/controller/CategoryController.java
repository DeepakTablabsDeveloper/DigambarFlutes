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

import com.ecom.core.domain.JsonObjectFormat;
import com.ecom.core.dto.Categories;
import com.ecom.core.dto.CategoriesWithSubCategories;
import com.ecom.core.dto.Children;
import com.ecom.core.dto.SubCategory;
import com.ecom.core.exception.CustomParameterizedException;
import com.ecom.core.repository.CategoriesRepository;
import com.ecom.core.repository.CustomersRepository;
import com.ecom.core.repository.ProductsRepository;
import com.ecom.core.repository.SubCategoryRepository;
import com.ecom.core.util.FireBaseNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "CategoriesController", description = "Categories details")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class CategoryController {
	
	private final Logger log = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	CategoriesRepository CategoriesDetailsRepository;
	
	@Autowired
	ProductsRepository productDetailsRepository;
	
	@Autowired
	FireBaseNotifications fireBaseNotifications;

	@Autowired
	CustomersRepository CustomersDetailsRepository;
	
	@Autowired
	SubCategoryRepository SubCategoryRepository;

	@PostMapping("/Categories")
	public ResponseEntity<String> saveCategories(@RequestBody Categories Categories) throws JsonProcessingException {
		log.debug("REST request to save Category Details : {}", Categories);
		if(Categories.getName()==null)
		{
			CustomParameterizedException customException=new CustomParameterizedException("name can't be null", "500");
            ObjectMapper Obj = new ObjectMapper(); 
			 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
	 		return ResponseEntity.ok().body(customExceptionStr);
		}
		Categories carDetails =CategoriesDetailsRepository.findByName(Categories.getName());
		if(carDetails!=null)
		{
			if(carDetails.getStaticId()==3)
			{
				CustomParameterizedException customException=new CustomParameterizedException("category already registered", "500");
	            ObjectMapper Obj = new ObjectMapper(); 
				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 		return ResponseEntity.ok().body(customExceptionStr);
			}else if(carDetails.getStaticId()==1)
			{
				CustomParameterizedException customException=new CustomParameterizedException("brand already registered", "500");
	            ObjectMapper Obj = new ObjectMapper(); 
				 String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
		 		return ResponseEntity.ok().body(customExceptionStr);
			}
		}
		
		Categories cat = CategoriesDetailsRepository.save(Categories);

		if (cat != null) {
			// that means queue already exists
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Categories saved successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(cat);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Categories not saved successfully");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@PutMapping("/Categories")
	public ResponseEntity<String> updateCategories(@RequestBody Categories Categories) throws JsonProcessingException {
		log.debug("REST request to update Attendance : {}", Categories);
		Categories temp=CategoriesDetailsRepository.findById(Categories.getId());
		if (temp== null) {
			CustomParameterizedException customException = new CustomParameterizedException("Invalid Id", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}

		
		
			if(Categories.getDescription()!=null)
				temp.setDescription(Categories.getDescription());
			if(Categories.getIsAvailable()!=null)
				temp.setIsAvailable(Categories.getIsAvailable());
			if(Categories.getName()!=null)
				temp.setName(Categories.getName());
			
		Categories CategoriesDetails = CategoriesDetailsRepository.save(temp);
		if (CategoriesDetails != null) {
			// that means queue already exists
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Categories updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(CategoriesDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Categories not updated successfully");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@GetMapping("Categories")
	public ResponseEntity<String> getAllCategories(@RequestParam(value = "page", required = false) Integer page,
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
		List<Categories> obj = CategoriesDetailsRepository.findAllCategories();
		System.out.println(" obj "+obj.size());
		if(!obj.isEmpty())
		{
		Page<Categories> result= new PageImpl<Categories>(obj, pageable, obj.size());
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got all Categories successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(result);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Categories not found");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}

	@GetMapping("/Categories/List")
	public ResponseEntity<String> getAllCategoriesList() throws JsonProcessingException {
		List<Categories> data = CategoriesDetailsRepository.findAll();
		if(!data.isEmpty())
		{
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got all Categories Details successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(data);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Categories not found");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}
	
	@GetMapping("/Categories/staticIs")
	public ResponseEntity<String> getAllCategoriesByStaticId(@RequestParam("staticId")Long staticId) throws JsonProcessingException {
		List<Categories> data = CategoriesDetailsRepository.findByStaticId(staticId);
		if(data.size()!=0)
		{
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Got data successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(data);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Data not found");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}

	}
	
	@GetMapping("Categories/brands")
	public ResponseEntity<String> getAllBrands(@RequestParam(value = "page", required = false) Integer page,
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
		List<Categories> obj = CategoriesDetailsRepository.findAllBrands();
		Page<Categories> result= new PageImpl<Categories>(obj, pageable, obj.size());
		if(result.hasContent())
		{
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got all brands successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(result);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("brands not found");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}
	
	

	@GetMapping("/Categories/Id/")
	public ResponseEntity<String> getCategoriesBasedOnId(@RequestParam(value = "id", required = true) Long id) throws JsonProcessingException {
		
	
		log.debug("REST request to get Categories : {}", id);
		Categories pageCategories = CategoriesDetailsRepository.findById(id);
		if(pageCategories!=null)
		{
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got data successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(pageCategories);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		}else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("data not found");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}
	
	@GetMapping("/Categories/subCategories")
	public ResponseEntity<String> getCategoriesWithSubCategories() throws JsonProcessingException {
		
		List<Categories> listCategories = CategoriesDetailsRepository.findAll();
		List<CategoriesWithSubCategories> result= new ArrayList<CategoriesWithSubCategories>();
		for(int i=0;i<listCategories.size();i++)
		{
		
			List<SubCategory> listSubCategory=SubCategoryRepository.findByCategoryId(listCategories.get(i).getId());
			List<Children> listChildren=new ArrayList<Children>();
			for(int j=0;j<listSubCategory.size();j++)
			{
				Children child=new Children("/shop/collection/left/sidebar", listSubCategory.get(j).getName(),"link");
				listChildren.add(child);
			}
			CategoriesWithSubCategories list=new CategoriesWithSubCategories(listCategories.get(i).getName(),"sub", false, listChildren);
			result.add(list);
		}
		/*JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got Category Details successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(result);*/
     	ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(result);
		return ResponseEntity.ok().body(customExceptionStr);
	}

	@Transactional
	@DeleteMapping("/Categories")
	public ResponseEntity<String> deleteCategories(@RequestParam("id") Long id, HttpServletRequest request)
			throws JsonProcessingException {
		log.debug("REST request to delete Categories : {}", id);
		try {
			CategoriesDetailsRepository.deleteById(id);
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


}
