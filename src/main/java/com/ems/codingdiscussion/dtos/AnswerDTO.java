package com.ems.codingdiscussion.dtos;

import lombok.Data;

@Data
public class AnswerDTO {
	
	private Long id;
	
	private String body;
	
	private Long questionId;
	
	private Long userId;
	
	

}
