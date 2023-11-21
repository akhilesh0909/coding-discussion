package com.ems.codingdiscussion.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OtpEmail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
        
    @Column(name = "one_time_password")
    private String oneTimePassword;
     
    @Column(name = "otp_requested_time")
    private long otpRequestedTime;

}
