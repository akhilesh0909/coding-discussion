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

	@Query(nativeQuery = true, value = "select * from questions q WHERE q.id in(select t.questions_id from questions_tags t where t.tags LIKE '%java')")
	List<Questions> findByTagsLike(@Param("tag") String tag);

	void save(QuestionDTO questionDTO);

	List<Questions> findByUser_id(Long userId);

}
