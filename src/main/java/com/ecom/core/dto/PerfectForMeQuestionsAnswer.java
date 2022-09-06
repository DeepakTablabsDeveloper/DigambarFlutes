package com.ecom.core.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="perfectformeAnswer")
public class PerfectForMeQuestionsAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="answers")
	private String answer;
	@Column(name="questionId")
	private Long questionId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public PerfectForMeQuestionsAnswer(Long id, String answer,Long questionId) {
		super();
		this.id = id;
		this.answer = answer;
		this.questionId = questionId;
	}
	public PerfectForMeQuestionsAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}
}
