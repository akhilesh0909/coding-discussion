package com.ems.codingdiscussion.dtos;

import java.util.List;

import lombok.Data;

@Data
public class SingleQuestionDTO {

	private QuestionDTO questionDTO;
	
	private List<AnswerDTO> answerDTOList;
}
