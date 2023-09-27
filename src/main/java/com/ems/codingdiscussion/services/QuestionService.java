package com.ems.codingdiscussion.services;

import java.util.List;

import com.ems.codingdiscussion.dtos.AllQuestionResponseDTO;
import com.ems.codingdiscussion.dtos.QuestionDTO;
import com.ems.codingdiscussion.dtos.SingleQuestionDTO;
import com.ems.codingdiscussion.entities.Questions;

public interface QuestionService {

	QuestionDTO addQuestion(QuestionDTO questionDTO);

	AllQuestionResponseDTO getAllQuestions(int pageNumber);

	SingleQuestionDTO getQuestionById(Long questionId);

	List<Questions> getQuestionListBySearch(int pageNumber, String askedQuestion);

}
