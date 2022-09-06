package com.ecom.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.core.dto.PerfectForMeQuestions;
import com.ecom.core.dto.PerfectForMeQuestionsAnswer;
import com.ecom.core.dto.PerfectForMeResponseDTO;
import com.ecom.core.repository.PerfectForMeAnswerRepository;
import com.ecom.core.repository.PerfectForMeQuestionRepository;

@Service
public class PerfectForMeServiceImpl implements PerfectForMeService {

	@Autowired
	PerfectForMeAnswerRepository answerRepository;
	
	@Autowired
	PerfectForMeQuestionRepository questionRepository;
	
	@Override
	public PerfectForMeQuestions addQuestion(PerfectForMeQuestions question) {
		return questionRepository.save(question);
	}

	@Override
	public PerfectForMeQuestions getQuestionById(Long id) {
		return questionRepository.findOne(id);
	}

	@Override
	public PerfectForMeQuestionsAnswer addAnswer(PerfectForMeQuestionsAnswer answer) {
		return answerRepository.save(answer);
	}

	@Override
	public PerfectForMeQuestionsAnswer getAnswerById(Long id) {
		return answerRepository.findOne(id);
	}

	@Override
	public PerfectForMeResponseDTO getQuestionAnswerByQuestionId(Long id) {
		PerfectForMeQuestions question=questionRepository.findOne(id);
		if(question!=null) {
			List<PerfectForMeQuestionsAnswer> answers=answerRepository.findByQuestionId(id);
			List<String> answerList=new ArrayList<>();
			for(PerfectForMeQuestionsAnswer ans:answers) {
				answerList.add(ans.getAnswer());
			}
			return new PerfectForMeResponseDTO(question.getQuestion(),answerList);
		}
		return null;
	}

	@Override
	public List<PerfectForMeResponseDTO> getAllQuestionAnswerByQuestionId() {
		List<PerfectForMeQuestions> questionList=(List<PerfectForMeQuestions>) questionRepository.findAll();
		if(!questionList.isEmpty()) {
			List<PerfectForMeResponseDTO> responseDTO=new ArrayList<>();
			for(PerfectForMeQuestions question:questionList) {
				PerfectForMeResponseDTO dto=new PerfectForMeResponseDTO();
				List<PerfectForMeQuestionsAnswer> answers=answerRepository.findByQuestionId(question.getId());
				List<String> answerList=new ArrayList<>();
				for(PerfectForMeQuestionsAnswer ans:answers) {
					answerList.add(ans.getAnswer());
				}
				dto.setId(question.getId());
				dto.setQuestion(question.getQuestion());
				dto.setAnswers(answerList);
				responseDTO.add(dto);
			}
			return responseDTO;
		}
		return null;
	}

	@Override
	public PerfectForMeQuestions removeQuestion(Long questionId) {
		PerfectForMeQuestions question=questionRepository.findOne(questionId);
		if(question!=null) {
		questionRepository.delete(question);
		List<PerfectForMeQuestionsAnswer> answers=answerRepository.findByQuestionId(question.getId());
		for(PerfectForMeQuestionsAnswer ans:answers) {
			answerRepository.delete(ans);	
		}
		return question;
		}
		return null;
	}

	@Override
	public PerfectForMeQuestions updateQuestion(PerfectForMeQuestions question) {
		if(question!=null && question.getId()!=null && question.getId()!=0) {
		PerfectForMeQuestions questionObj=questionRepository.findOne(question.getId());
		
		    if(question.getQuestion()!=null) {
		    	questionObj.setQuestion(question.getQuestion());
		    }
		    if(question.getIsActive()!=null) {
		    	questionObj.setIsActive(question.getIsActive());
		    }
		    if(question.getSequence()!=0) {
		    	questionObj.setSequence(question.getSequence());
		    }
		    questionRepository.save(questionObj);
		    return questionObj;
		}
		return null;
	}
}
