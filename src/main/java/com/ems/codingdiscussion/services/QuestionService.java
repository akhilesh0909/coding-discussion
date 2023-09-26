package com.ems.codingdiscussion.services;

import com.ems.codingdiscussion.dtos.AllQuestionResponseDTO;
import com.ems.codingdiscussion.dtos.QuestionDTO;

public interface QuestionService {

	QuestionDTO addQuestion(QuestionDTO questionDTO);

	AllQuestionResponseDTO getAllQuestions(int pageNumber);

}
