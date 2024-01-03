package com.ems.codingdiscussion.schedulers;

import com.ems.codingdiscussion.entities.Answers;
import com.ems.codingdiscussion.entities.User;
import com.ems.codingdiscussion.repositories.AnswerRepository;
import com.ems.codingdiscussion.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerApprovalTask {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    public void markAnswerAsApproved() throws Exception{
        List<Answers> answers = answerRepository.findAll();
        List<Long> answerIds = answers.stream().filter((ans) -> {
            if (ans.getVotes() >= 5){
               Integer adminVotes = userRepository.adminVotes(ans.getVotedUsers());
                return adminVotes >= 2;
            }
            return false;
        }).map(Answers::getId).toList();

        answerRepository.markAsApproved(answerIds);
    }
}
