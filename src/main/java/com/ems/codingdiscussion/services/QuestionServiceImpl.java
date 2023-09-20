package com.ems.codingdiscussion.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.codingdiscussion.dtos.QuestionDTO;
import com.ems.codingdiscussion.entities.Questions;
import com.ems.codingdiscussion.entities.User;
import com.ems.codingdiscussion.repositories.QuestionRepository;
import com.ems.codingdiscussion.repositories.UserRepository;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public QuestionDTO addQuestion(QuestionDTO questionDTO) {
		Optional<User> optUser = userRepository.findById(questionDTO.getUserId());
		if(optUser.isPresent()) {
			Questions question = new Questions();
			question.setTitle(questionDTO.getTitle());
			question.setBody(questionDTO.getBody());
			question.setTags(questionDTO.getTags());
			question.setCreatedDate(new Date());
			Questions createdQuestion = questionRepository.save(question);
			QuestionDTO createdQuestionDTO = new QuestionDTO();
			createdQuestionDTO.setId(createdQuestion.getId());
			createdQuestionDTO.setTitle(createdQuestion.getTitle());
			return createdQuestionDTO;
		}
		return null;
	}
}
