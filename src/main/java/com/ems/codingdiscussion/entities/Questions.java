package com.ems.codingdiscussion.entities;

import java.util.Date;
import java.util.List;

import com.ems.codingdiscussion.dtos.QuestionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Questions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String title;
	
	@Lob
	@Column(name = "body", length = 512)
	private String body;
	
	private Date createdDate;
	
	@ElementCollection(targetClass=String.class)
	private List<String> tags;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	public QuestionDTO getQuestionDTO() {
		 QuestionDTO questionDto= new QuestionDTO();
		 questionDto.setId(id);
		 questionDto.setTitle(title);
		 questionDto.setBody(body);
		 questionDto.setTags(tags);
		 questionDto.setUserId(user.getId());
		 questionDto.setUserName(user.getName()); 
		 questionDto.setCreatedDate(createdDate);
		 return questionDto;
		 
	}
	
}
