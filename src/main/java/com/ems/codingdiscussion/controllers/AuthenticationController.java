package com.ems.codingdiscussion.controllers;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.NotAcceptableStatusException;

import com.ems.codingdiscussion.dtos.AuthDTO;
import com.ems.codingdiscussion.dtos.LoginDTO;
import com.ems.codingdiscussion.dtos.ResetPassword;
import com.ems.codingdiscussion.dtos.UserDTO;
import com.ems.codingdiscussion.dtos.ValidateOtpDTO;
import com.ems.codingdiscussion.entities.User;
import com.ems.codingdiscussion.repositories.UserRepository;
import com.ems.codingdiscussion.services.EmailSenderService;
import com.ems.codingdiscussion.services.UserService;
import com.ems.codingdiscussion.utils.JWTUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private EmailSenderService emailService;
	
	@Autowired
	private UserService userService;
	
	public static final String TOKEN_PREFIX ="Bearer ";
	public static final String HEADER_STRING ="Authorization";
	
	@PostMapping("/authenticate")
	public void createJwtToken(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws Exception,DisabledException,BadCredentialsException{
		
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
		}
		catch(BadCredentialsException e){
	    	throw new BadCredentialsException("Email or Password not valid");	
		}
		catch(DisabledException disabledException) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,"USER IS NOT CREATED");
			return;
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
		
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
		final String jwtToken = jwtUtil.generateToken(userDetails);
		
		if(optionalUser.isPresent()) {
			response.getWriter().write(new JSONObject().put("userId", optionalUser.get().getId()).append("isAdmin", String.valueOf(optionalUser.get().isAdmin())).toString());
			//response.getWriter().write(new JSONObject().put("isAdmin", optionalUser.get().isAdmin()).toString());
		}
		response.addHeader("Access-Control-Exppose-Headers", "Authorization");
		response.setHeader("Access-Control-Allow-Headers", "Authorization,X-PINGOTHER,X-Requested-With,Content-Type, Accept,X-Custom-header");
		
		response.setHeader(HEADER_STRING, TOKEN_PREFIX +jwtToken);
		
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity<?> createOTP(@RequestBody String email, HttpServletResponse response) throws Exception{

		if(!userService.isValidEmail(email))
			return new ResponseEntity<>("Only thalesgroup Email is valid",HttpStatus.NOT_ACCEPTABLE);
		try {
			this.emailService.sendMail(email);
		}catch(NoSuchElementException ex){
			return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			return new ResponseEntity<>("Something went wrong",HttpStatus.GATEWAY_TIMEOUT);
		}
			
		return new ResponseEntity<>("OTP Sent Successfully", HttpStatus.OK);
		
	}
	
	@PostMapping("/validate-otp")
	public ResponseEntity<?> validateOTP(@RequestBody ValidateOtpDTO validateOtpDTO, HttpServletResponse response) throws Exception{
		
		if(!userService.isValidOTP(validateOtpDTO))
			return new ResponseEntity<>("OTP is not valid or expired", HttpStatus.NOT_ACCEPTABLE);
			
		return new ResponseEntity<>("OTP validation Successful", HttpStatus.OK);
		
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPassword resetPassword, HttpServletResponse response) throws UsernameNotFoundException{
		UserDTO newUser = null;
		try {
			newUser = userService.resetPassword(resetPassword);
		} catch (UsernameNotFoundException e) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
			
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		
	}
}
