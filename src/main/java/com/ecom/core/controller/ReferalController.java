package com.ecom.core.controller;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.ecom.core.dto.Discount;
import com.ecom.core.dto.ReferralIndo;
import com.ecom.core.repository.CartRepository;
import com.ecom.core.repository.DiscountRepository;
import com.ecom.core.service.ReferralService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "ReferelController", description = "Referal Data")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ReferalController {

	@Autowired
	ReferralService referralService;

	@Autowired
	CartRepository cartDetailsRepository;

	@Autowired
	DiscountRepository discountRepository;

	@PostMapping("/assignReferralCode")
	public ResponseEntity<String> createRandomCode(@RequestBody ReferralIndo referral) throws JsonProcessingException {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new SecureRandom();
		for (int i = 0; i < 9; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String referralCode = sb.toString();

		referral.setReferralCode("DKF" + referralCode);
		referral.setIsActive("active");

		ReferralIndo ref = referralService.saveReferral(referral);
		if (ref != null) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Referral Code Generated");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(ref);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Unable to create Referral Code");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData(ref);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@GetMapping("/validateRefferralCode")
	public ResponseEntity<?> verifiedRederralCode(@RequestParam("referralCode") String referralCode,
			@RequestParam("totalAmount") Long amount) throws JsonProcessingException {
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		ReferralIndo referralInfo = referralService.validateReferralCode(referralCode, date);
		if (referralInfo != null) {
			referralInfo.getValiDateTo().after(date);
			System.out.println(" referralInfo.getValidDateFrom().after(date) "+referralInfo.getValidDateFrom().compareTo(date)+"--"+date.compareTo(referralInfo.getValidDateFrom())
					+ date.after(referralInfo.getValidDateFrom()) + " referralInfo.getValiDateTo().before(date) "+referralInfo.getValiDateTo()+" "+date
					+ date.before(referralInfo.getValiDateTo()));

			if ((date.after(referralInfo.getValidDateFrom()) && date.before(referralInfo.getValiDateTo())) || (date.compareTo(referralInfo.getValidDateFrom())==-1 && date.compareTo(referralInfo.getValiDateTo())==-1)) {

				if (referralInfo.getDiscountId() != null && referralInfo.getDiscountId() != 0) {
					Discount discount = discountRepository.findById(referralInfo.getDiscountId());

					Long totalAmount = (amount / 100) * discount.getPercentageOfBaseCost();
					Map<String, Long> totalCost = new HashMap<>();
					totalCost.put("Total Amount", amount-totalAmount);

					jsonobjectFormat.setMessage("Validate Successfully");
					jsonobjectFormat.setSuccess(true);
					jsonobjectFormat.setData(totalCost);
					ObjectMapper Obj = new ObjectMapper();
					String customExceptionStr = Obj.writerWithDefaultPrettyPrinter()
							.writeValueAsString(jsonobjectFormat);
					return ResponseEntity.ok().body(customExceptionStr);
				} else {
					System.out.println(" Here 2");
				}
			} else {
				jsonobjectFormat.setMessage("Referral Code Not in Date Rang");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
		} else {
			jsonobjectFormat.setMessage("Invalid Referral Code");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		return null;
	}

	@GetMapping("/getAllReferralCode")
	public ResponseEntity<String> getAllRandomCode() throws JsonProcessingException {

		List<ReferralIndo> allReferralCode = referralService.getAllReferral();
		if (allReferralCode != null && !allReferralCode.isEmpty()) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("All Referral Code");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(allReferralCode);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Referral Code Not Found");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@GetMapping("/inactiveActiveReferralCode")
	public ResponseEntity<String> inactiveActiveRandomCode(@RequestParam("referralCode") String referralCode)
			throws JsonProcessingException {

		ReferralIndo referral = referralService.findReferralByReferralCode(referralCode);
		if (referral != null) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Referral status has been updated");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(referral);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Unable to update Referral status");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}
	
	
	@DeleteMapping("/removeReferralCode")
	public ResponseEntity<String> removeReferralCode(@RequestParam("referralCode") String referralCode)
			throws JsonProcessingException {

		ReferralIndo referral = referralService.removeReferralCode(referralCode);
		if (referral != null) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Referral Code Removed");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(referral);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Unable to Remove Referral Code");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}
	
	
	@PutMapping("/updateReferral")
	public ResponseEntity<String> updateReferralCode(@RequestBody ReferralIndo referralRequest)
			throws JsonProcessingException {

		ReferralIndo referral = referralService.updateReferralCode(referralRequest);
		if (referral != null) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Referral Code Updated");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(referral);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		} else {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Unable to update Referral Code");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}
		
}
