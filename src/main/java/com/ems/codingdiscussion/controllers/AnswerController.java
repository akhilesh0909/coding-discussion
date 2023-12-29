package com.ems.codingdiscussion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ems.codingdiscussion.dtos.AnswerDTO;
import com.ems.codingdiscussion.services.AnswerService;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
	
	@Autowired
	private AnswerService answerService;
	
	@PostMapping
	public ResponseEntity<?> addAnswer(@RequestBody AnswerDTO answerDTO){
		AnswerDTO ansDto = answerService.postAnswer(answerDTO);
		if(ansDto == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(ansDto);
	}

	@PatchMapping("/submit-upvote/{userId}/{answerId}/{isUpvoting}")
	public ResponseEntity<?> submitUpvote(@PathVariable Long userId, @PathVariable Long answerId, @PathVariable Boolean isUpvoting){
		try {
			answerService.submitUpvote(userId, answerId, isUpvoting);
		}catch (Exception e){
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Upvote submitted successfully", HttpStatus.OK);
	}
}
