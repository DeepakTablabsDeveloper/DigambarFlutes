package com.ecom.core.service;

import java.util.List;

import com.ecom.core.dto.PerfectForMeQuestions;
import com.ecom.core.dto.PerfectForMeQuestionsAnswer;
import com.ecom.core.dto.PerfectForMeResponseDTO;

public interface PerfectForMeService {

	PerfectForMeQuestions addQuestion(PerfectForMeQuestions question);

	PerfectForMeQuestions getQuestionById(Long id);

	PerfectForMeQuestionsAnswer addAnswer(PerfectForMeQuestionsAnswer answer);

	PerfectForMeQuestionsAnswer getAnswerById(Long id);

	PerfectForMeResponseDTO getQuestionAnswerByQuestionId(Long id);

	List<PerfectForMeResponseDTO> getAllQuestionAnswerByQuestionId();

	PerfectForMeQuestions removeQuestion(Long questionId);

	PerfectForMeQuestions updateQuestion(PerfectForMeQuestions question);

}
