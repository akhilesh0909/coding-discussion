package com.ems.codingdiscussion.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ems.codingdiscussion.services.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurationDB {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailServiceImpl();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(encoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		return security
				.csrf()
				.disable()
				.authorizeHttpRequests()
				.requestMatchers("/sign-up")
				.permitAll()
				.and()
				.authorizeHttpRequests()
				.requestMatchers("/user","/","/admin")
				.authenticated()
				.and()
				.formLogin()
				.and()
				.build();
		
	}

}
