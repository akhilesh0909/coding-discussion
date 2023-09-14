package com.ems.codingdiscussion.controllers;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ems.codingdiscussion.dtos.AuthDTO;
import com.ems.codingdiscussion.dtos.LoginDTO;
import com.ems.codingdiscussion.entities.User;
import com.ems.codingdiscussion.repositories.UserRepository;
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
			response.getWriter().write(new JSONObject().put("userId", optionalUser.get().getId()).toString());
		}
		response.addHeader("Access-Control-Exppose-Headers", "Authorization");
		response.setHeader("Access-Control-Allow-Headers", "Authorization,X-PINGOTHER,X-Requested-With,Content-Type, Accept,X-Custom-header");
		
		response.setHeader(HEADER_STRING, TOKEN_PREFIX +jwtToken);
		
	}
	
	
}
