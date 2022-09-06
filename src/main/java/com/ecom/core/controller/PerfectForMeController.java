package com.ecom.core.controller;

import java.util.List;

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
import com.ecom.core.dto.PerfectForMeQuestions;
import com.ecom.core.dto.PerfectForMeQuestionsAnswer;
import com.ecom.core.dto.PerfectForMeResponseDTO;
import com.ecom.core.service.PerfectForMeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@RestController
@Api(value = "PerfectForMeController", description = "Filter Product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class PerfectForMeController {

	@Autowired
	PerfectForMeService service;
	
	@PostMapping("/perferctforme/add/question")
	public ResponseEntity<String> addQuestion(@RequestBody PerfectForMeQuestions question) throws JsonProcessingException{
		PerfectForMeQuestions questionResponse=service.addQuestion(question);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		if(questionResponse!=null) {
			jsonobjectFormat.setMessage("Question Saved");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(questionResponse);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
			}else {
				jsonobjectFormat.setMessage("Unable to Save Question");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
	} 
	
	@GetMapping("/perferctforme/getQuestion")
	public ResponseEntity<String> getQuestion(@RequestParam Long id) throws JsonProcessingException{
		PerfectForMeQuestions questionResponse=service.getQuestionById(id);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		if(questionResponse!=null) {
			jsonobjectFormat.setMessage("Question Fetch Successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(questionResponse);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
			}else {
				jsonobjectFormat.setMessage("No Date Found");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
	} 
	
	
	@PostMapping("/perferctforme/add/answer")
	public ResponseEntity<String> addAnswer(@RequestBody PerfectForMeQuestionsAnswer answer) throws JsonProcessingException{
		PerfectForMeQuestionsAnswer answerObj=service.addAnswer(answer);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		if(answerObj!=null) {
			jsonobjectFormat.setMessage("Answer Saved");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(answerObj);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
			}else {
				jsonobjectFormat.setMessage("Unable to Save Answer");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
	} 
	
	
	@GetMapping("/perferctforme/getAnswer")
	public ResponseEntity<String> getAnswer(@RequestParam Long id) throws JsonProcessingException{
		PerfectForMeQuestionsAnswer answerResponse=service.getAnswerById(id);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		if(answerResponse!=null) {
			jsonobjectFormat.setMessage("Answer Fetch Successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(answerResponse);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
			}else {
				jsonobjectFormat.setMessage("No Date Found");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
	} 
	
	
	@GetMapping("/perferctforme/getQuestionAnswer")
	public ResponseEntity<String> getQuestionAnswer(@RequestParam Long id) throws JsonProcessingException{
		PerfectForMeResponseDTO response=service.getQuestionAnswerByQuestionId(id);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		if(response!=null) {
			jsonobjectFormat.setMessage("Data Fetch Successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(response);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
			}else {
				jsonobjectFormat.setMessage("No Date Found");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
	} 
	
	@GetMapping("/perferctforme/getAllQuestionAnswer")
	public ResponseEntity<String> getAllQuestionAnswer() throws JsonProcessingException{
		List<PerfectForMeResponseDTO> response=service.getAllQuestionAnswerByQuestionId();
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		if(response!=null) {
			jsonobjectFormat.setMessage("Data Fetch Successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(response);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
			}else {
				jsonobjectFormat.setMessage("No Date Found");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
	} 
	
	@DeleteMapping("/perferctforme/removeQuestion")
	public ResponseEntity<String> removeQuestion(@RequestParam("questionId") Long questionId) throws JsonProcessingException{
		PerfectForMeQuestions response=service.removeQuestion(questionId);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		if(response!=null) {
			jsonobjectFormat.setMessage("Question deleted");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(response);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
			}else {
				jsonobjectFormat.setMessage("Unable to delete Question");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
	} 
	
	
	@PutMapping("/perferctforme/updateQuestion")
	public ResponseEntity<String> updateQuestion(@RequestBody PerfectForMeQuestions question) throws JsonProcessingException{
		PerfectForMeQuestions response=service.updateQuestion(question);
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		if(response!=null) {
			jsonobjectFormat.setMessage("Question Updated");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(response);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
			}else {
				jsonobjectFormat.setMessage("Unable to Update Question");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
	} 
}
