package com.sts.attendenceapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService()
	{
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		http.authorizeRequests()
		.antMatchers("/register","/do_register","/signin","/dashboard","/resetpassword","/reset","/role/**","/dept/**","/images/**","/css/**").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/signin").defaultSuccessUrl("/dashboard")
		.and().csrf().disable();
		*/	
	
		http.authorizeRequests()
 		.antMatchers("/signin","/images/**","/css/**","/resetpassword","/reset").permitAll()
		.antMatchers("/register,do_register").hasRole("TESTER") //ok
  		.antMatchers("/dashboard").hasAnyRole("TESTER","HR") //ok
 		.antMatchers(HttpMethod.POST,"/role/addRole,/dept/addDept").hasRole("TESTER")
 		.antMatchers(HttpMethod.GET,"/role/getRoles,/dept/getDepts").hasRole("TESTER")
 		.antMatchers(HttpMethod.GET,"/employee/getemployees").hasRole("TESTER")

 		.anyRequest().authenticated();
//		.and().formLogin().loginPage("/signin").defaultSuccessUrl("/dashboard");
 
		http.csrf().disable()
		.cors().disable();

		
		http.headers().frameOptions().disable();

	}

	
}
