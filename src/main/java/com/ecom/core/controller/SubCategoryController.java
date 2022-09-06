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
import com.ecom.core.dto.Categories;
import com.ecom.core.dto.SubCategory;
import com.ecom.core.exception.CustomParameterizedException;
import com.ecom.core.repository.CategoriesRepository;
import com.ecom.core.repository.SubCategoryRepository;
import com.ecom.core.util.FireBaseNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "SubCategoryController", description = "SubCategory details")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class SubCategoryController {
	
	private final Logger log = LoggerFactory.getLogger(SubCategoryController.class);

	@Autowired
	SubCategoryRepository SubCategoryDetailsRepository;
	
	@Autowired
	FireBaseNotifications fireBaseNotifications;



	@Autowired
	CategoriesRepository CategoriesRepository;

	@PostMapping("/SubCategory")
	public ResponseEntity<String> saveSubCategory(@RequestBody SubCategory SubCategory) throws JsonProcessingException {
		log.debug("REST request to save subCategory Details : {}", SubCategory);
		if(SubCategory.getId()!=null)
		{
			CustomParameterizedException customException = new CustomParameterizedException("Invalid Id", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		Categories category =CategoriesRepository.findById(SubCategory.getCategoryId());
		if(category==null)
		{
			CustomParameterizedException customException = new CustomParameterizedException("Invalid Category", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		SubCategory carDetails = SubCategoryDetailsRepository.save(SubCategory);
	

		if (carDetails.getId() != null) {
			// that means queue already exists
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("SubCategory saved successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(carDetails);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("SubCategory not saved successfully");
			jsonobjectFormat.setSuccess(false);
		
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@PutMapping("/SubCategory")
	public ResponseEntity<String> updateSubCategory(@RequestBody SubCategory SubCategory) throws JsonProcessingException {
		log.debug("REST request to update Attendance : {}", SubCategory);
		if (!(SubCategory.getId() != null)) {
			CustomParameterizedException customException = new CustomParameterizedException("Invalid Id", "500");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		SubCategory temp=SubCategoryDetailsRepository.findById(SubCategory.getId());
		if (temp.getId() != null) {
			if(SubCategory.getCategoryId()!=null)
			{
				Categories category =CategoriesRepository.findById(SubCategory.getCategoryId());
				if(category==null)
				{
					CustomParameterizedException customException = new CustomParameterizedException("Invalid Category", "500");
					ObjectMapper Obj = new ObjectMapper();
					String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(customException);
					return ResponseEntity.ok().body(customExceptionStr);
				}
				else
					temp.setCategoryId(SubCategory.getCategoryId());
			}
			if(SubCategory.getDescription()!=null)
				temp.setDescription(SubCategory.getDescription());
			if(SubCategory.getIsAvailable()!=null)
				temp.setIsAvailable(SubCategory.getIsAvailable());
			if(SubCategory.getName()!=null)
				temp.setName(SubCategory.getName());
			
		SubCategory result = SubCategoryDetailsRepository.save(temp);
			// that means queue already exists
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("SubCategory updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(result);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("SubCategory not updated successfully");
			jsonobjectFormat.setSuccess(false);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@GetMapping("SubCategory")
	public ResponseEntity<String> getAllSubCategory(@RequestParam(value = "page", required = false) Integer page,
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
		List<SubCategory> obj = SubCategoryDetailsRepository.findAll();
		for(int i=0;i<obj.size();i++)
		{
			
			Categories category =CategoriesRepository.findById(obj.get(i).getCategoryId());
			if(category!=null)
				obj.get(i).setCategoryName(category.getName());
		}
		Page<SubCategory> result= new PageImpl<>(obj, pageable, obj.size());
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got SubCategory By PageFormat successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(result);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
		
	}

	@GetMapping("/SubCategory/List")
	public ResponseEntity<String> getAllSubCategoryList() throws JsonProcessingException {
		List<SubCategory> data = SubCategoryDetailsRepository.findAll();
		for(int i=0;i<data.size();i++)
		{
			
			Categories category =CategoriesRepository.findById(data.get(i).getCategoryId());
			if(category!=null)
				data.get(i).setCategoryName(category.getName());
		}
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got all SubCategory Details successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(data);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);

	}

	@GetMapping("/SubCategory/Id")
	public ResponseEntity<String> getSubCategoryBasedOnId(@RequestParam(value = "id", required = true) Long id) throws JsonProcessingException {
	
	
		log.debug("REST request to get Attendance : {}", id);
		SubCategory data = SubCategoryDetailsRepository.findById(id);
		
			
		Categories category =CategoriesRepository.findById(data.getCategoryId());
		if(category!=null)
			data.setCategoryName(category.getName());
	
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got subCategory Details successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(data);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
	}
	
	@GetMapping("/SubCategory/categoryId")
	public ResponseEntity<String> getSubCategoryBasedOnCategoryId(@RequestParam(value = "categoryId", required = true) Long categoryId) throws JsonProcessingException {
	
	
		log.debug("REST request to get Attendance : {}", categoryId);
		List<SubCategory> data = SubCategoryDetailsRepository.findByCategoryId(categoryId);
		
		for(int i=0;i<data.size();i++)
		{
			Categories category =CategoriesRepository.findById(data.get(i).getCategoryId());
			if(category!=null)
				data.get(i).setCategoryName(category.getName());
		}
			
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("Got subCategory Details successfully");
		jsonobjectFormat.setSuccess(true);
		jsonobjectFormat.setData(data);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
	}

	@Transactional
	@DeleteMapping("/SubCategory")
	public ResponseEntity<String> deleteSubCategory(@RequestParam("id") Long id, HttpServletRequest request)
			throws JsonProcessingException {
		log.debug("REST request to delete SubCategory : {}", id);
		try {
			SubCategoryDetailsRepository.deleteById(id);
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
