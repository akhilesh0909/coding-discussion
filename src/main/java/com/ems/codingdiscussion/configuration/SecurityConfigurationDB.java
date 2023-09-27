package com.ems.codingdiscussion.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ems.codingdiscussion.filters.JWTRequestFilter;
import com.ems.codingdiscussion.services.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurationDB {
	
	@Autowired
	private JWTRequestFilter jwtRequestFilter;
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(encoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailServiceImpl);
		return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		return security
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.csrf()
				.disable()
				.authorizeHttpRequests()
				.requestMatchers("/sign-up","/authenticate","/api/askedQuestion/**")
				.permitAll()
				.and()
				.authorizeHttpRequests()
				.requestMatchers("/admin","/api/**","/user","/")
				.authenticated()
				.and()
				.formLogin()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.build();
		
	}

}
