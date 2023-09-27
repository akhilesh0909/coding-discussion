package com.ems.codingdiscussion.services;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ems.codingdiscussion.dtos.AllQuestionResponseDTO;
import com.ems.codingdiscussion.dtos.QuestionDTO;
import com.ems.codingdiscussion.dtos.SingleQuestionDTO;
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
	public static final int SEARCH_RESULT_PER_PAGE =5;
	@Override
	public QuestionDTO addQuestion(QuestionDTO questionDTO) {
		Optional<User> optUser = userRepository.findById(questionDTO.getUserId());
		if(optUser.isPresent()) {
			Questions question = new Questions();
			question.setTitle(questionDTO.getTitle());
			question.setBody(questionDTO.getBody());
			question.setTags(questionDTO.getTags());
			question.setCreatedDate(new Date());
			question.setUser(optUser.get());
			Questions createdQuestion = questionRepository.save(question);
			QuestionDTO createdQuestionDTO = new QuestionDTO();
			createdQuestionDTO.setId(createdQuestion.getId());
			createdQuestionDTO.setTitle(createdQuestion.getTitle());
			return createdQuestionDTO;
		}
		return null;
	}

	@Override
	public AllQuestionResponseDTO getAllQuestions(int pageNumber) {
		Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
		Page<Questions> questionPage=questionRepository.findAll(paging);
		
		AllQuestionResponseDTO allQuestionResponseDTO = new AllQuestionResponseDTO();
		allQuestionResponseDTO.setQuestionDTOlist(questionPage.getContent().stream().map(Questions::getQuestionDTO).collect(Collectors.toList()));
		allQuestionResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
		allQuestionResponseDTO.setTotalPages(questionPage.getTotalPages());
		return allQuestionResponseDTO;
	}

	@Override
	public SingleQuestionDTO getQuestionById(Long questionId) {
		Optional<Questions> optionalQuestion = questionRepository.findById(questionId);
		SingleQuestionDTO singleQuestionDTO = new SingleQuestionDTO();
		optionalQuestion.ifPresent(question -> singleQuestionDTO.setQuestionDTO(question.getQuestionDTO()));
		return singleQuestionDTO;
	}
}
