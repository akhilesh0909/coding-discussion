package com.ems.codingdiscussion.services;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ems.codingdiscussion.entities.Answers;
import com.ems.codingdiscussion.entities.Image;
import com.ems.codingdiscussion.repositories.AnswerRepository;
import com.ems.codingdiscussion.repositories.ImageRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public void storeFile(MultipartFile multipartFile, Long answerId) throws IOException {
		Optional<Answers> optionalAnswer = answerRepository.findById(answerId);
		if(optionalAnswer.isPresent()) {
			String fileName = org.springframework.util.StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
			Image image = new Image();
			image.setName(fileName);
			image.setAnswer(optionalAnswer.get());
			image.setType(multipartFile.getContentType());
			image.setData(multipartFile.getBytes());
			imageRepository.save(image);
			
		}else {
			throw new IOException("Answer not found");
		}
		
	}
	
	

}
