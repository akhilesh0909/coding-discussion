package com.ems.codingdiscussion.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.codingdiscussion.dtos.AnswerDTO;
import com.ems.codingdiscussion.entities.Answers;
import com.ems.codingdiscussion.entities.Questions;
import com.ems.codingdiscussion.entities.User;
import com.ems.codingdiscussion.repositories.AnswerRepository;
import com.ems.codingdiscussion.repositories.QuestionRepository;
import com.ems.codingdiscussion.repositories.UserRepository;


@Service
public class AnswerServiceImpl implements AnswerService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	

	@Override
	public AnswerDTO postAnswer(AnswerDTO answerDTO) {
		Optional<User> optionalUser = userRepository.findById(answerDTO.getUserId());
		Optional<Questions> optionalQuestion = questionRepository.findById(answerDTO.getQuestionId());
		if(optionalUser.isPresent() && optionalQuestion.isPresent()) {
			Answers answers = new Answers();
			answers.setBody(answerDTO.getBody());
			answers.setCreatedDate(new Date());
			answers.setUser(optionalUser.get());
			answers.setQuestion(optionalQuestion.get());
			Answers createdAnswers = answerRepository.save(answers);
			AnswerDTO ansDto = new AnswerDTO();
			ansDto.setId(createdAnswers.getId());
			return ansDto;
		}
		return null;
	}

}
