package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ecom.core.dto.PerfectForMeQuestionsAnswer;

public interface PerfectForMeAnswerRepository extends PagingAndSortingRepository<PerfectForMeQuestionsAnswer, Long> {

	@Query(value="select * from perfectforme_answer where question_id=?",nativeQuery = true)
	List<PerfectForMeQuestionsAnswer> findByQuestionId(Long id);

}
