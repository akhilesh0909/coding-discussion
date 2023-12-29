package com.ems.codingdiscussion.services;

import com.ems.codingdiscussion.dtos.AnswerDTO;

public interface AnswerService {

	AnswerDTO postAnswer(AnswerDTO answerDTO);

    void submitUpvote(Long userId, Long answerId, Boolean isUpvoting) throws Exception;
}
