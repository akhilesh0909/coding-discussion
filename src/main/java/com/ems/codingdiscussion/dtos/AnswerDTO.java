package com.ems.codingdiscussion.dtos;

import java.util.Date;

import com.ems.codingdiscussion.entities.Image;

import lombok.Data;

@Data
public class AnswerDTO {
	
	private Long id;
	
	private String body;
	
	private Date createdDate;
	
	private Long questionId;
	
	private Long userId;
	
	private String userName;
	
	private Image file;
	
	

}
