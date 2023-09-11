//package com.ems.codingdiscussion.configuration;
//
//
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfigurationJDBC {
//	
//	@Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//            .setType(EmbeddedDatabaseType.H2)
//            .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//            .build();
//    }
//	
//    @Bean
//    public UserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.withDefaultPasswordEncoder()
//            .username("anika")
//            .password("kallu")
//            .roles("BOSS")
//            .build();
//        
//        UserDetails user2 = User.withDefaultPasswordEncoder()
//                .username("aarti")
//                .password("patel")
//                .roles("ADMIN")
//                .build();
//        
//        UserDetails user3 = User.withDefaultPasswordEncoder()
//                .username("akhilesh")
//                .password("bhatt")
//                .roles("USER")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        users.createUser(user2);
//        users.createUser(user3);
//        return users;
//    }
//    
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
//		return security
//				.csrf()
//				.disable()
//				.authorizeHttpRequests()
//				.requestMatchers("/admin")
//				.hasRole("ADMIN")
//				.and()
//				.authorizeHttpRequests()
//				.requestMatchers("/user")
//				.hasRole("USER")
//				.and()
//				.authorizeHttpRequests()
//				.requestMatchers("/**")
//				.hasRole("BOSS")
//				.and()
//				.formLogin()
//				.and()
//				.build();
//		
//	}
//
//}
