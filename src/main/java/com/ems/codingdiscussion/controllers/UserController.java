package com.ems.codingdiscussion.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.ems.codingdiscussion.dtos.UserDTO;
import com.ems.codingdiscussion.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<UserDTO> allUserResponse = userService.getAllUsers();
		return ResponseEntity.ok(allUserResponse);
	}
	
	@PostMapping("/user/{userId}/make-admin")
	public ResponseEntity<?> makeAdmin(@PathVariable Long userId)  throws Exception{
		UserDTO userDTO;
		try {
			userDTO = userService.makeAdmin(userId);
		} catch (UsernameNotFoundException e) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.EXPECTATION_FAILED);
		}
		return ResponseEntity.ok(userDTO);
	}
	
	@PostMapping("/user/{userId}/toggle-access")
	public ResponseEntity<?> toggleUserAccess(@PathVariable Long userId)  throws Exception{
		UserDTO userDTO;
		try {
			userDTO = userService.toggleUserAccess(userId);
		}
		catch(UsernameNotFoundException ex){
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.EXPECTATION_FAILED);
		}
		return ResponseEntity.ok(userDTO);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUser(@PathVariable Long userId) throws Exception {
		UserDTO userDTO;
		try {
			userDTO = userService.getUser(userId);
		} catch (UsernameNotFoundException ex) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.EXPECTATION_FAILED);
		}
		return ResponseEntity.ok(userDTO);
	}

	@PatchMapping("/user/{userId}/{newUserName}")
	public ResponseEntity<?> changeUserName(@PathVariable Long userId,@PathVariable String newUserName) throws Exception {
		UserDTO userDTO;
		try {
			userDTO = userService.changeUserName(userId,newUserName);
		} catch (UsernameNotFoundException ex) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.EXPECTATION_FAILED);
		}
		return ResponseEntity.ok(userDTO);
	}

}
