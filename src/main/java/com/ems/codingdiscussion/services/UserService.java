package com.ems.codingdiscussion.services;

import java.util.List;

import com.ems.codingdiscussion.dtos.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

	UserDTO createUser(SignupDTO signupDTO);

	boolean hasUserWithEmail(String email);
	
	boolean isValidEmail(String email);
	
	boolean isValidOTP(ValidateOtpDTO validateOtpDTO);
	
	UserDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws UsernameNotFoundException;

	List<UserDTO> getAllUsers();

	UserDTO makeAdmin(Long userId) throws UsernameNotFoundException, Exception;

	UserDTO toggleUserAccess(Long userId) throws UsernameNotFoundException, Exception;

	public void saveOtp(int otp, String email);

    UserDTO getUser(Long userId) throws Exception;

	UserDTO changeUserName(Long userId, String userName) throws Exception;

	void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception;
}
