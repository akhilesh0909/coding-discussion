package com.ems.codingdiscussion.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ems.codingdiscussion.services.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@PostMapping("/image/{answerId}")
	public ResponseEntity<String> uploadFile(@RequestParam MultipartFile multipartFile, @PathVariable Long answerId){
		
		try {
			imageService.storeFile(multipartFile, answerId);
			return ResponseEntity.ok("Image stored successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		  
		}
		
		
		
	}

}
