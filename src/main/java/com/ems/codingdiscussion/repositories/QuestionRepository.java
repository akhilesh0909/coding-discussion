package com.ems.codingdiscussion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.codingdiscussion.entities.Questions;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {

	List<Questions> findByTags(String tag);

}
