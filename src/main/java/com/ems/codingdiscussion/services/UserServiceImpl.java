package com.ems.codingdiscussion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ems.codingdiscussion.dtos.SignupDTO;
import com.ems.codingdiscussion.dtos.UserDTO;
import com.ems.codingdiscussion.entities.User;
import com.ems.codingdiscussion.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDTO createUser(SignupDTO signupDTO) {
		User user = new User();
		user.setEmail(signupDTO.getEmail());
		user.setName(signupDTO.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
		User createdUser = userRepository.save(user);
		UserDTO userDTO = new UserDTO();
		userDTO.setId(createdUser.getId());
		userDTO.setEmail(createdUser.getEmail());
		userDTO.setName(createdUser.getName());
		return userDTO;
	}

	@Override
	public boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}

}
