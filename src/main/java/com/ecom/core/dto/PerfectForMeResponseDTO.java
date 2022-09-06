package com.ecom.core.dto;

import java.util.List;

public class PerfectForMeResponseDTO {
    private Long id;
	private String question;
	private List<String> answers;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<String> getAnswers() {
		return answers;
	}
	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	public PerfectForMeResponseDTO(String question, List<String> answers) {
		super();
		this.question = question;
		this.answers = answers;
	}
	public PerfectForMeResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
