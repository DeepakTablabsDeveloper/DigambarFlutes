package com.ecom.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="perfectformeQuestion")
public class PerfectForMeQuestions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="question")
	private String question;
	@Column(name="sequence")
	private int sequence;
	@Column(name="isActive")
	private Boolean isActive;
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
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
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public PerfectForMeQuestions(Long id, String question, int sequence) {
		super();
		this.id = id;
		this.question = question;
		this.sequence = sequence;
	}
	public PerfectForMeQuestions() {
		super();
		// TODO Auto-generated constructor stub
	}
}
