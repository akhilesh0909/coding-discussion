package com.ems.codingdiscussion.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.ems.codingdiscussion.dtos.AllQuestionResponseDTO;
import com.ems.codingdiscussion.dtos.AnswerDTO;
import com.ems.codingdiscussion.dtos.QuestionDTO;
import com.ems.codingdiscussion.dtos.SingleQuestionDTO;
import com.ems.codingdiscussion.entities.Answers;
import com.ems.codingdiscussion.entities.Questions;
import com.ems.codingdiscussion.entities.User;
import com.ems.codingdiscussion.repositories.AnswerRepository;
import com.ems.codingdiscussion.repositories.ImageRepository;
import com.ems.codingdiscussion.repositories.QuestionRepository;
import com.ems.codingdiscussion.repositories.UserRepository;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private EmailSenderService emailSenderService;
	
	public static final int SEARCH_RESULT_PER_PAGE =5;
	
	@Override
	public QuestionDTO addQuestion(QuestionDTO questionDTO) {
		Optional<User> optUser = userRepository.findById(questionDTO.getUserId());
		if(optUser.isPresent()) {
			Questions question = new Questions();
			question.setTitle(questionDTO.getTitle());
			question.setBody(questionDTO.getBody());
			question.setTags(questionDTO.getTags());
			question.setCreatedDate(new Date());
			question.setUser(optUser.get());
			Questions createdQuestion = questionRepository.save(question);
			new Thread(() -> {
				try {
					String subject = "Question posted on EMS-DISCUSSION-PORTAL";
					String body = "One question posted by " + question.getUser().getEmail() + "\n" +
							"Click on URL to view \n" +
							"http://sm-ems-discussion-portal.smtools.sentinelcloud.com/#/user/dashboard \n" +
							questionDTO.getTitle();
					SimpleMailMessage message=new SimpleMailMessage();
					message.setFrom("EMS-CODING-DISCUSSION@THALESGROUP.COM");
					message.setText(body);
					message.setSubject(subject);
					List<String> userEmails = userRepository.findAllByRole(optUser.get().getRole()).stream().map(User::getEmail).filter((email) -> !email.equalsIgnoreCase(question.getUser().getEmail())).toList();
					message.setTo(userEmails.toArray(new String[0]));
					emailSenderService.sendMail(message);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}).start();
			QuestionDTO createdQuestionDTO = new QuestionDTO();
			createdQuestionDTO.setId(createdQuestion.getId());
			createdQuestionDTO.setTitle(createdQuestion.getTitle());
			return createdQuestionDTO;
		}
		return null;
	}

	@Override
	public AllQuestionResponseDTO getAllQuestions(int pageNumber) {
		Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
		Page<Questions> questionPage=questionRepository.findAll(paging);
		
		AllQuestionResponseDTO allQuestionResponseDTO = new AllQuestionResponseDTO();
		allQuestionResponseDTO.setQuestionDTOlist(questionPage.getContent().stream().map(Questions::getQuestionDTO).collect(Collectors.toList()));
		allQuestionResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
		allQuestionResponseDTO.setTotalPages(questionPage.getTotalPages());
		return allQuestionResponseDTO;
	}

	@Override
	public SingleQuestionDTO getQuestionById(Long questionId) {
		Optional<Questions> optionalQuestion = questionRepository.findById(questionId);
		if (optionalQuestion.isPresent()) {
			SingleQuestionDTO singleQuestionDTO = new SingleQuestionDTO();
			List<AnswerDTO> answerDTOList = new ArrayList<>();
			singleQuestionDTO.setQuestionDTO(optionalQuestion.get().getQuestionDTO());
			List<Answers> answerList = answerRepository.findAllByQuestionId(questionId);
			for (Answers answer : answerList) {
				AnswerDTO answerDTO = answer.getAnswerDTO();
				answerDTO.setFile(imageRepository.findByAnswer(answer));
				answerDTOList.add(answerDTO);
			}
			singleQuestionDTO.setAnswerDTOList(answerDTOList);
			return singleQuestionDTO;

		}
		return null;
	}

	@Override
	public AllQuestionResponseDTO getQuestionListBySearch(String askedQuestion) {
		
		String[] words = askedQuestion.split(" ");
		
		AllQuestionResponseDTO allQuestionResponseDTO = new AllQuestionResponseDTO();

		List<Questions> questionList = new ArrayList<>();
		for(String word: words) {
			String trimmedWord = word.replaceAll("[^a-zA-Z]", "");
			if(!trimmedWord.isEmpty() && !trimmedWord.isBlank())
				questionList.addAll(questionRepository.findByTagsLike("%"+trimmedWord+"%"));
		}
		allQuestionResponseDTO.setQuestionDTOlist(questionList.stream().map(Questions::getQuestionDTO).collect(Collectors.toList()));
		
		return allQuestionResponseDTO;
	}

	@Override
	public QuestionDTO saveOrUpdate(QuestionDTO questionDTO) throws Exception {
		
		Optional<User> optUser = userRepository.findById(questionDTO.getUserId());
		Optional<Questions> optionalQuestion = questionRepository.findById(questionDTO.getId());

		if(optUser.isPresent() && optionalQuestion.isPresent()) {
			Questions question = new Questions();
			question.setTitle(questionDTO.getTitle());
			question.setBody(questionDTO.getBody());
			question.setTags(optionalQuestion.get().getTags());
			question.setCreatedDate(optionalQuestion.get().getCreatedDate());
			question.setUser(optUser.get());
			question.setId(questionDTO.getId());
			Questions createdQuestion = questionRepository.save(question);
			QuestionDTO createdQuestionDTO = new QuestionDTO();
			createdQuestionDTO.setId(createdQuestion.getId());
			createdQuestionDTO.setTitle(createdQuestion.getTitle());
			return createdQuestionDTO;
		}
		else {
			throw new Exception("Question doesn't exist ");
		}
	}

	@Override
	public AllQuestionResponseDTO getPostedQuestionList(Long userId) {
		
		List<Questions> questionList = questionRepository.findByUser_id(userId);
		AllQuestionResponseDTO allQuestionResponseDTO = new AllQuestionResponseDTO();
		allQuestionResponseDTO.setQuestionDTOlist(questionList.stream().map(Questions::getQuestionDTO).collect(Collectors.toList()));
		return allQuestionResponseDTO;
	}

	@Override
	public void deleteQuestion(Long userId, Long questionId) throws RuntimeException {

		userRepository.findById(userId).ifPresentOrElse((optUser) -> {
			if (optUser.isAdmin()){
				try {
					questionRepository.deleteById(questionId);
				}catch (IllegalArgumentException e){
					throw new RuntimeException("Something went wrong");
				}
			}
		}, () -> {
			throw new RuntimeException("Only admin can delete questions");
		});
	}
}
