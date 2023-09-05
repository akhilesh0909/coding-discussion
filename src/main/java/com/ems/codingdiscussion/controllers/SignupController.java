package com.ems.codingdiscussion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ems.codingdiscussion.dtos.SignupDTO;
import com.ems.codingdiscussion.dtos.UserDTO;
import com.ems.codingdiscussion.services.UserService;

@RestController
public class SignupController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping({"/sign-up"})
	public ResponseEntity<?> create(@RequestBody SignupDTO signupDTO){
		
		if (userService.hasUserWithEmail(signupDTO.getEmail())) {
			return new ResponseEntity<>("User with this email " + signupDTO.getEmail() + " is already exist", HttpStatus.NOT_ACCEPTABLE);
		}
		
		UserDTO createdUser = userService.createUser(signupDTO);
		
		if(createdUser == null)
			return new ResponseEntity<>("User not created, try again later", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

}
