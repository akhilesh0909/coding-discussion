package com.ems.codingdiscussion.entities;

import java.util.Date;

import com.ems.codingdiscussion.dtos.AnswerDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
public class Answers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(name = "body", length = 512)
	private String body;

	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "question_id", nullable = false)
	@JsonIgnore
	private Questions question;

	public AnswerDTO getAnswerDTO() {

		AnswerDTO answerDTO = new AnswerDTO();
		answerDTO.setId(id);
		answerDTO.setBody(body);
		answerDTO.setUserId(user.getId());
		answerDTO.setQuestionId(question.getId());
		answerDTO.setUserName(user.getName());
		answerDTO.setCreatedDate(createdDate);

		return answerDTO;

	}

}
