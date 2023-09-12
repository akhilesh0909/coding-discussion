package com.ems.codingdiscussion.dtos;

import lombok.Data;

@Data
public class AuthDTO {
	
	public AuthDTO(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}

	private String jwtToken;

}
