package com.ems.codingdiscussion.repositories;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ems.codingdiscussion.entities.Answers;

@Repository
public interface AnswerRepository extends JpaRepository<Answers, Long> {

	List<Answers> findAllByQuestionId(Long questionId);
	
	List<Answers> findByUser_id(Long userId);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true,value = "UPDATE answers SET is_approved=true where id IN :answerIds")
    void markAsApproved(@Param("answerIds") List<Long> answerIds);
}
