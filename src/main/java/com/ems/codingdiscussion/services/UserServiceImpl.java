package com.ems.codingdiscussion.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ems.codingdiscussion.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ems.codingdiscussion.entities.OtpEmail;
import com.ems.codingdiscussion.entities.User;
import com.ems.codingdiscussion.repositories.OtpEmailRepository;
import com.ems.codingdiscussion.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OtpEmailRepository otpEmailRepository;

	@Autowired
	private AuthenticationManager authenticationManager;
		
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;

	
	private enum EmailDomain {
		
		OF_CONTRACTOR("@external.thalesgroup.com"),
		OF_PERMANENT("@thalesgroup.com");
		
		public final String value;

	    private EmailDomain(String value) {
	        this.value = value;
	    }
	}

	@Override
	public UserDTO createUser(SignupDTO signupDTO) {
		User user = new User();
		user.setEmail(signupDTO.getEmail());
		user.setName(signupDTO.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
		user.setRole(signupDTO.getRole());
		User createdUser = userRepository.save(user);
		UserDTO userDTO = new UserDTO();
		userDTO.setId(createdUser.getId());
		userDTO.setEmail(createdUser.getEmail());
		userDTO.setName(createdUser.getName());
		userDTO.setRole(createdUser.getRole());
		userDTO.setAdmin(createdUser.isAdmin());
		userDTO.setLocked(createdUser.isLocked());
		return userDTO;
	}

	@Override
	public boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}

	@Override
	public boolean isValidEmail(String email) {
		
		if(email.contains("@")) {
			String subStringOfEmail = email.substring(email.indexOf("@"));
			
			if(subStringOfEmail.equalsIgnoreCase(EmailDomain.OF_CONTRACTOR.value)  || subStringOfEmail.equalsIgnoreCase(EmailDomain.OF_PERMANENT.value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isValidOTP(ValidateOtpDTO validateOtpDTO) {
		
		String email = validateOtpDTO.getEmail();
		String otp = validateOtpDTO.getOtp();
		
		Optional<OtpEmail> otpEmail = otpEmailRepository.findLatestByEmail(email);
		
		if(otpEmail.isPresent()) {
			long currentTimeInMillis = System.currentTimeMillis();
	        long otpRequestedTimeInMillis = otpEmail.get().getOtpRequestedTime();
	                
	        if (otpRequestedTimeInMillis + OTP_VALID_DURATION >= currentTimeInMillis) {
	            if(otp.equals(otpEmail.get().getOneTimePassword())) {
	            	return true;
	            }
	        }
		}
		return false;
	}

	@Override
	public UserDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws UsernameNotFoundException {
		
		User user = new User();
		userRepository.findFirstByEmail(resetPasswordDTO.getEmail()).ifPresentOrElse(userOptional -> {
			user.setName(userOptional.getName());
			user.setId(userOptional.getId());
			user.setAdmin(userOptional.isAdmin());
			user.setLocked(userOptional.isLocked());
			user.setRole(userOptional.getRole());
		}, () -> {
			throw new UsernameNotFoundException("User not found");
		});

		user.setEmail(resetPasswordDTO.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(resetPasswordDTO.getPassword()));
		User newUser = userRepository.save(user);
		UserDTO userDTO = new UserDTO();
		userDTO.setId(newUser.getId());
		userDTO.setEmail(newUser.getEmail());
		userDTO.setName(newUser.getName());
		userDTO.setAdmin(newUser.isAdmin());
		userDTO.setLocked(user.isLocked());
		userDTO.setRole(user.getRole());
		return userDTO;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTO> userDTOs = new ArrayList<>();
		
		for(User user: users) {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setEmail(user.getEmail());
			userDTO.setName(user.getName());
			userDTO.setAdmin(user.isAdmin());
			userDTO.setLocked(user.isLocked());
			userDTO.setRole(user.getRole());
			userDTOs.add(userDTO);
		}
		
		return userDTOs;
	}

	@Override
	public UserDTO makeAdmin(Long userId) throws UsernameNotFoundException,Exception {
		User user = new User();
		userRepository.findById(userId).ifPresentOrElse(userOptional -> {
			user.setName(userOptional.getName());
			user.setId(userOptional.getId());
			user.setEmail(userOptional.getEmail());
			user.setRole(userOptional.getRole());
		}, () -> {
			throw new UsernameNotFoundException("User not found");
		});
		try {
			userRepository.makeAdmin(userId);
		}catch(Exception e) {
			throw new Exception("Something went wrong");
		}
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setName(user.getName());
		userDTO.setAdmin(true);
		userDTO.setLocked(false);
		userDTO.setRole(user.getRole());
		return userDTO;
	}

	@Override
	public UserDTO toggleUserAccess(Long userId) throws UsernameNotFoundException, Exception {
		User user = new User();
		userRepository.findById(userId).ifPresentOrElse(userOptional -> {
			user.setName(userOptional.getName());
			user.setId(userOptional.getId());
			user.setEmail(userOptional.getEmail());
			user.setAdmin(userOptional.isAdmin());
			user.setLocked(userOptional.isLocked());
			user.setRole(userOptional.getRole());
		}, () -> {
			throw new UsernameNotFoundException("User not found");
		});
		try {
			userRepository.toggleUserAccess(userId,!user.isLocked());
		}catch(Exception e) {
			throw new Exception("Something went wrong");
		}
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setName(user.getName());
		userDTO.setAdmin(user.isAdmin());
		userDTO.setLocked(!user.isLocked());
		userDTO.setRole(user.getRole());
		return userDTO;
		
	}

	@Override
	public void saveOtp(int otp, String email) {
		OtpEmail otpEmail = new OtpEmail();

		otpEmail.setEmail(email);
		otpEmail.setOneTimePassword(String.valueOf(otp));
		otpEmail.setOtpRequestedTime(System.currentTimeMillis());

		otpEmailRepository.save(otpEmail);
	}

	@Override
	public UserDTO getUser(Long userId) throws Exception {

		User user = new User();
		UserDTO userDTO = new UserDTO();
		userRepository.findById(userId).ifPresentOrElse(userOptional -> {
			user.setName(userOptional.getName());
			user.setId(userOptional.getId());
			user.setEmail(userOptional.getEmail());
			user.setAdmin(userOptional.isAdmin());
			user.setLocked(userOptional.isLocked());
			user.setRole(userOptional.getRole());
			userDTO.setId(user.getId());
			userDTO.setEmail(user.getEmail());
			userDTO.setName(user.getName());
			userDTO.setAdmin(user.isAdmin());
			userDTO.setLocked(user.isLocked());
			userDTO.setRole(user.getRole());
		}, () -> {
			throw new UsernameNotFoundException("User not found");
		});
		return userDTO;
	}

	@Override
	public UserDTO changeUserName(Long userId, String userName) throws Exception {
		UserDTO userDTO = new UserDTO();
		userRepository.findById(userId).ifPresentOrElse((optUser) -> {
			userDTO.setId(optUser.getId());
			userDTO.setRole(optUser.getRole());
			userDTO.setEmail(optUser.getEmail());
			userDTO.setName(userName);
			userDTO.setLocked(optUser.isLocked());
			userDTO.setAdmin(optUser.isAdmin());
			try {
				userRepository.updateUserName(userId, userName);
			}catch (Exception e){
				throw new RuntimeException("Username not changed");
			}
		}, () -> {
			throw new RuntimeException("User not found");
		});
		return userDTO;
	}

	@Override
	public void changePassword(ChangePasswordDTO changePasswordDTO) throws BadCredentialsException,UsernameNotFoundException {
		User user = new User();
		userRepository.findFirstByEmail(changePasswordDTO.getEmail()).ifPresentOrElse((userOptional)-> {
			user.setName(userOptional.getName());
			user.setId(userOptional.getId());
			user.setAdmin(userOptional.isAdmin());
			user.setLocked(userOptional.isLocked());
			user.setRole(userOptional.getRole());
		},() -> {
			throw new UsernameNotFoundException("User not found");
		});
		Authentication auth;
		try {
			auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(changePasswordDTO.getEmail(),changePasswordDTO.getOldPassword()));
		} catch(BadCredentialsException e){
			throw new BadCredentialsException("Incorrect Old Password Provided");
		}
		if(auth.isAuthenticated()) {
			user.setEmail(changePasswordDTO.getEmail());
			user.setPassword(new BCryptPasswordEncoder().encode(changePasswordDTO.getNewPassword()));
			userRepository.save(user);
		}
	}


}
