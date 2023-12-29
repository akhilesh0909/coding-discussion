package com.ems.codingdiscussion.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.ems.codingdiscussion.dtos.AnswerDTO;
import com.ems.codingdiscussion.entities.Answers;
import com.ems.codingdiscussion.entities.Questions;
import com.ems.codingdiscussion.entities.User;
import com.ems.codingdiscussion.repositories.AnswerRepository;
import com.ems.codingdiscussion.repositories.QuestionRepository;
import com.ems.codingdiscussion.repositories.UserRepository;


@Service
public class AnswerServiceImpl implements AnswerService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private EmailSenderService emailSenderService;
	

	@Override
	public AnswerDTO postAnswer(AnswerDTO answerDTO) {
		Optional<User> optionalUser = userRepository.findById(answerDTO.getUserId());
		Optional<Questions> optionalQuestion = questionRepository.findById(answerDTO.getQuestionId());
		if(optionalUser.isPresent() && optionalQuestion.isPresent()) {
			Answers answers = new Answers();
			answers.setBody(answerDTO.getBody());
			answers.setCreatedDate(new Date());
			answers.setUser(optionalUser.get());
			answers.setQuestion(optionalQuestion.get());
			Answers createdAnswers = answerRepository.save(answers);
			new Thread(() -> {
				try {
					String subject = "Answer posted on EMS-DISCUSSION-PORTAL";
					String body = "One Answer posted by " + answers.getUser().getEmail() + "\n" +
							"Click on URL to view \n" +
							"http://sm-ems-discussion-portal.smtools.sentinelcloud.com/#/user/dashboard";
					SimpleMailMessage message=new SimpleMailMessage();
					message.setFrom("EMS-CODING-DISCUSSION@THALESGROUP.COM");
					message.setText(body);
					message.setSubject(subject);
					message.setTo(optionalQuestion.get().getUser().getEmail());
					emailSenderService.sendMail(message);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}).start();

			AnswerDTO ansDto = new AnswerDTO();
			ansDto.setId(createdAnswers.getId());
			return ansDto;
		}
		return null;
	}

	@Override
	public void submitUpvote(Long userId, Long answerId, Boolean isUpvoting) throws Exception {
		Answers answers = new Answers();
		answerRepository.findById(answerId).ifPresentOrElse((optAnswer) -> {
			if(userRepository.findById(userId).isPresent()) {
				try {
						answers.setId(answerId);
						answers.setBody(optAnswer.getBody());
						answers.setCreatedDate(optAnswer.getCreatedDate());
						answers.setUser(optAnswer.getUser());
						answers.setIsApproved(optAnswer.getIsApproved());
						answers.setQuestion(optAnswer.getQuestion());
						if (isUpvoting) {
							answers.setVotes(optAnswer.getVotes() + 1);
							optAnswer.getVotedUsers().add(userId);
							answers.setVotedUsers(optAnswer.getVotedUsers());
						}else {
							answers.setVotes(optAnswer.getVotes() - 1);
							optAnswer.getVotedUsers().remove(userId);
							answers.setVotedUsers(optAnswer.getVotedUsers());
						}
						answerRepository.save(answers);
				}catch (Exception e){
					throw new RuntimeException("Something went wrong");
				}
			}
		},() -> {
			throw new RuntimeException("Something went wrong");
		});
	}

}
