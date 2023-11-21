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
	
    Random random = new Random(1000);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OtpEmailRepository otpEmailRepository;
	
	@Autowired
    private JavaMailSender mailSender;

	@Override
	public void sendMail(String toEmail) throws Exception {
		
		userRepository.findFirstByEmail(toEmail).orElseThrow();
		
		int otp = random.nextInt(999999);
		
		String subject = "OTP from Coding Discussion Portal";
		String body = "OTP : " + otp;
		SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("CodingDiscussion-EMS@thalesgroup.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        
        try {
			mailSender.send(message);
		} catch (MailException e) {
			throw new Exception("Mail not sent");
		}
        
        OtpEmail otpEmail = new OtpEmail();
        
        otpEmail.setEmail(toEmail);
        otpEmail.setOneTimePassword(String.valueOf(otp));
        otpEmail.setOtpRequestedTime(System.currentTimeMillis());
        
        otpEmailRepository.save(otpEmail);
        
		
	}

}
