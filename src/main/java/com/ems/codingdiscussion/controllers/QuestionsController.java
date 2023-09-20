package com.ems.codingdiscussion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.codingdiscussion.dtos.QuestionDTO;
import com.ems.codingdiscussion.services.QuestionService;

@RestController
@RequestMapping("api/question")
public class QuestionsController {

	@Autowired
	private QuestionService questionService;
	
	public ResponseEntity<?> post(@RequestBody QuestionDTO questionDTO){
		
		QuestionDTO createdQuestionDTO = questionService.addQuestion(questionDTO);
		if(createdQuestionDTO == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDTO);
		
	}
}
