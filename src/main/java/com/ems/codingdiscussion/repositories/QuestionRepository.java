package com.ems.codingdiscussion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.codingdiscussion.entities.Questions;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {

}
