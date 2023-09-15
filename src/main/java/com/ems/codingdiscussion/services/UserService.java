package com.ems.codingdiscussion.services;

import com.ems.codingdiscussion.dtos.SignupDTO;
import com.ems.codingdiscussion.dtos.UserDTO;

public interface UserService {

	UserDTO createUser(SignupDTO signupDTO);

	boolean hasUserWithEmail(String email);
	
	boolean isValidEmail(String email);

}
