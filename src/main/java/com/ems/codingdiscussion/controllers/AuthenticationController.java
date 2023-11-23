package com.ems.codingdiscussion.controllers;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import com.ems.codingdiscussion.dtos.LoginDTO;
import com.ems.codingdiscussion.dtos.ResetPassword;
import com.ems.codingdiscussion.dtos.UserDTO;
import com.ems.codingdiscussion.dtos.ValidateOtpDTO;
import com.ems.codingdiscussion.entities.User;
import com.ems.codingdiscussion.repositories.UserRepository;
import com.ems.codingdiscussion.services.EmailSenderService;
import com.ems.codingdiscussion.services.UserService;
import com.ems.codingdiscussion.utils.JWTUtil;

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
	public ResponseEntity<?> createJwtToken(@RequestBody LoginDTO loginDTO) throws Exception,DisabledException,BadCredentialsException{
		
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
		}
		catch(BadCredentialsException e){
	    	//throw new BadCredentialsException("Email or Password not valid");	
	    	return new ResponseEntity<>("Email or Password not valid",HttpStatus.BAD_REQUEST);
		}
		catch(DisabledException disabledException) {
			//response.sendError(HttpServletResponse.SC_NOT_FOUND,"USER IS NOT CREATED");
			//return;
			return new ResponseEntity<>("User not created",HttpStatus.NOT_FOUND);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
		
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
		if(optionalUser.get().isLocked()) {
			//response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,"User locked");
			return new ResponseEntity<>("User locked", HttpStatus.LOCKED);
		}
		final String jwtToken = jwtUtil.generateToken(userDetails);
		
		if(optionalUser.isPresent()) {
			//response.getWriter().write(new JSONObject().put("userId", optionalUser.get().getId()).append("isAdmin", String.valueOf(optionalUser.get().isAdmin())).toString());
			//response.getWriter().write(new JSONObject().put("isAdmin", optionalUser.get().isAdmin()).toString());
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.add("Access-Control-Exppose-Headers", "Authorization");
		responseHeaders.set("Access-Control-Allow-Headers", "Authorization,X-PINGOTHER,X-Requested-With,Content-Type, Accept,X-Custom-header");
		
		responseHeaders.set(HEADER_STRING, TOKEN_PREFIX +jwtToken);
		
		return ResponseEntity.ok().headers(responseHeaders).body(new JSONObject().put("userId", optionalUser.get().getId()).append("isAdmin", String.valueOf(optionalUser.get().isAdmin())).toString());
		
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity<?> createOTP(@RequestBody String email) throws Exception{

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
	public ResponseEntity<?> validateOTP(@RequestBody ValidateOtpDTO validateOtpDTO) throws Exception{
		
		if(!userService.isValidOTP(validateOtpDTO))
			return new ResponseEntity<>("OTP is not valid or expired", HttpStatus.NOT_ACCEPTABLE);
			
		return new ResponseEntity<>("OTP validation Successful", HttpStatus.OK);
		
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPassword resetPassword) throws UsernameNotFoundException{
		UserDTO newUser = null;
		try {
			newUser = userService.resetPassword(resetPassword);
		} catch (UsernameNotFoundException e) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
			
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		
	}
}
