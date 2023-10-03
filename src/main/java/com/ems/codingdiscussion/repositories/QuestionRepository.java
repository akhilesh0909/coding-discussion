package com.ems.codingdiscussion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ems.codingdiscussion.dtos.QuestionDTO;
import com.ems.codingdiscussion.entities.Questions;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {

	@Query(nativeQuery = true, value = "select q1_0.id,q1_0.body,q1_0.created_date,q1_0.title,q1_0.user_id from t_questions q1_0 WHERE q1_0.id in(select t1_0.questions_id from questions_tags t1_0 where t1_0.tags LIKE :tag)")
	List<Questions> findByTagsLike(@Param("tag") String tag);

	void save(QuestionDTO questionDTO);

}
