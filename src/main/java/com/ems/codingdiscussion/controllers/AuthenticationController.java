package com.ems.codingdiscussion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ems.codingdiscussion.dtos.AuthDTO;
import com.ems.codingdiscussion.dtos.LoginDTO;
import com.ems.codingdiscussion.utils.JWTUtil;

@RestController
public class AuthenticationController {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createJwtToken(@RequestBody LoginDTO loginDTO) throws Exception{
		
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
		}
		catch(BadCredentialsException e){
			throw new Exception("Email or Password not valid", e);
			
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
		
		final String jwtToken = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthDTO(jwtToken));
	}
	
	
}
