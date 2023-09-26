package com.ems.codingdiscussion.dtos;

import java.util.List;

import lombok.Data;

@Data
public class AllQuestionResponseDTO {
	
	private List<QuestionDTO> questionDTOlist;
	
	private Integer totalPages;
	
	private Integer pageNumber;
	
}
