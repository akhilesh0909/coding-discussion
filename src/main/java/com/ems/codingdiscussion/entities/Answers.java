package com.ems.codingdiscussion.entities;

import java.util.Date;

import com.ems.codingdiscussion.dtos.AnswerDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
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
