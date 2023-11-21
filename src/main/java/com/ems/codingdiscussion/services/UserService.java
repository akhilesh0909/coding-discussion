package com.ems.codingdiscussion.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.NotAcceptableStatusException;

import com.ems.codingdiscussion.dtos.ResetPassword;
import com.ems.codingdiscussion.dtos.SignupDTO;
import com.ems.codingdiscussion.dtos.UserDTO;
import com.ems.codingdiscussion.dtos.ValidateOtpDTO;

public interface UserService {

	UserDTO createUser(SignupDTO signupDTO);

	boolean hasUserWithEmail(String email);
	
	boolean isValidEmail(String email);
	
	boolean isValidOTP(ValidateOtpDTO validateOtpDTO);
	
	UserDTO resetPassword(ResetPassword resetPassword) throws UsernameNotFoundException;

}
