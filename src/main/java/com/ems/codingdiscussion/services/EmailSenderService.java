package com.ems.codingdiscussion.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {

	void sendMail(SimpleMailMessage message) throws Exception;

}
