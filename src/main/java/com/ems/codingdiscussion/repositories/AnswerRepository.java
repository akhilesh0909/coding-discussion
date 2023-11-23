package com.ems.codingdiscussion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.codingdiscussion.entities.Answers;

@Repository
public interface AnswerRepository extends JpaRepository<Answers, Long> {

	List<Answers> findAllByQuestionId(Long questionId);
	
	List<Answers> findByUser_id(Long userId);

}
