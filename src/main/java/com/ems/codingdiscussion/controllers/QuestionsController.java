package com.ems.codingdiscussion.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.codingdiscussion.dtos.AllQuestionResponseDTO;
import com.ems.codingdiscussion.dtos.QuestionDTO;
import com.ems.codingdiscussion.dtos.SingleQuestionDTO;
import com.ems.codingdiscussion.entities.Questions;
import com.ems.codingdiscussion.services.QuestionService;

@RestController
@RequestMapping("/api")
public class QuestionsController {

	@Autowired
	private QuestionService questionService;
	
	@PostMapping("/question")
	public ResponseEntity<?> post(@RequestBody QuestionDTO questionDTO){
		
		QuestionDTO createdQuestionDTO = questionService.addQuestion(questionDTO);
		if(createdQuestionDTO == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDTO);
		
	}
	
	@GetMapping("/questions/{pageNumber}")
	public ResponseEntity<AllQuestionResponseDTO> getAllQuestions(@PathVariable int pageNumber){
		AllQuestionResponseDTO allQuestionResponse = questionService.getAllQuestions(pageNumber);
		return ResponseEntity.ok(allQuestionResponse);
	}
	
	@GetMapping("/question/{questionId}")
	public ResponseEntity<?> getQuestionById(@PathVariable Long questionId){
		SingleQuestionDTO singleQuestionDTO = questionService.getQuestionById(questionId);
		if(singleQuestionDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(singleQuestionDTO);
	}
	
	@PutMapping("/edit/question/{questionId}")  
	public ResponseEntity<?> updateQuestion(@PathVariable Long questionId,@RequestBody QuestionDTO questionDTO) throws Exception   
	{  
	   questionDTO.setId(questionId);
	   QuestionDTO createdQuestionDTO = questionService.saveOrUpdate(questionDTO);
	   
	   if(createdQuestionDTO == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDTO);
	}  
	
	@PostMapping("/askedQuestion")
	public ResponseEntity<?> getQuestionListBySearch(@RequestBody String askedQuestion){
		AllQuestionResponseDTO askedQuestionResponse = questionService.getQuestionListBySearch(askedQuestion);
		if(askedQuestionResponse == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(askedQuestionResponse);
	}
}
