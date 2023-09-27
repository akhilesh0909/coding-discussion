package com.ems.codingdiscussion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.codingdiscussion.entities.Answers;
import com.ems.codingdiscussion.entities.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	Image findByAnswer(Answers answer);
	
	
	
	

}
