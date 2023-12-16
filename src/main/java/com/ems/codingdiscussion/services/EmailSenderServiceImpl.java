package com.ems.codingdiscussion.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.ems.codingdiscussion.entities.OtpEmail;
import com.ems.codingdiscussion.repositories.OtpEmailRepository;
import com.ems.codingdiscussion.repositories.UserRepository;

@Repository
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OtpEmailRepository otpEmailRepository;
	
	@Autowired
    private JavaMailSender mailSender;

	@Override
	public void sendMail(SimpleMailMessage message) throws Exception {

        for (String email: message.getTo())
            userRepository.findFirstByEmail(email).orElseThrow();
        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new Exception("Mail not sent");
        }
    }

}
