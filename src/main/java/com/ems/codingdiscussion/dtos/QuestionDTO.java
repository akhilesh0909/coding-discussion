package com.ems.codingdiscussion.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class QuestionDTO {
	
	private long id;
	
	private String title;
	
	private String body;
	
	private List<String> tags;
	
	private Long userId;

	private String userName;
	
	private Date createdDate; 
	
}
