//package com.ems.codingdiscussion.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfigurationInmemory {
//	
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	UserDetails user = User
//			.withUsername("anika")
//			.password(encoder.encode("kallu"))
//			.roles("BOSS")
//			.build();
//	
//	UserDetails userTwo = User
//			.withUsername("akhilesh")
//			.password(encoder.encode("bhatt"))
//			.roles("ANIKA")
//			.build();
//			
//	return new InMemoryUserDetailsManager(user,userTwo);
//			
//	}
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
//		return security
//				.csrf()
//				.disable()
//				.authorizeHttpRequests()
//				.requestMatchers("/**")
//				.hasRole("BOSS")
//				.and()
//				.authorizeHttpRequests()
//				.requestMatchers("/user")
//				.hasRole("ANIKA")
//				.and()
//				.formLogin()
//				.and()
//				.build();
//		
//	}
//
//}
