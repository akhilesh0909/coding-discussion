package com.ems.codingdiscussion.dtos;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
	
	private String name;
		
	private String email;
	
	private boolean isAdmin;
	
	private boolean isLocked;

}
