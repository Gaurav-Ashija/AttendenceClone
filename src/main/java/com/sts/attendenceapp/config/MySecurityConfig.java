package com.sts.attendenceapp.config;
 
import java.io.File;


import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.entities.Login;
import com.sts.attendenceapp.repositories.EmployeeRepository;
import com.sts.attendenceapp.repositories.LoginRepository;
import com.sts.attendenceapp.services.audit.ImplAudit;
 
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplAudit audit;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private LoginRepository loginRepository;

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
 
		http.authorizeRequests()
 		.antMatchers("/signin","/images/**","/css/**","/resetpassword","/reset").permitAll()
		.antMatchers("/register","do_register").hasAnyRole("USER","HR") //ok
  		.antMatchers("/dashboard").hasAnyRole("USER","HR") //ok
  		.antMatchers("/hierarchy").hasAnyRole("USER","HR") //ok
   	
  		.and().httpBasic().and().authorizeRequests()
 		.antMatchers(HttpMethod.POST,"/role/addRole","/dept/addDept").hasRole("USER")
 		.antMatchers(HttpMethod.GET,"/role/getRoles","/dept/getDepts").hasRole("USER")
 		.antMatchers(HttpMethod.GET,"/employee/getemployees").hasRole("USER")
        .anyRequest().authenticated()	
  		.and()
        .addFilterBefore(getBeforeAuthenticationFilter(), CustomBeforeAuthenticationFilter.class)
  		.formLogin().loginPage("/signin")
  		.usernameParameter("username")
    		.defaultSuccessUrl("/dashboard")
    		.and()
 		 .logout()
         .logoutUrl("/logout")
  		.logoutSuccessHandler(logoutSuccessHandler())
        .permitAll();
 		http.csrf().disable().cors().disable();
		http.headers().frameOptions().disable();

	}
	
	 public UsernamePasswordAuthenticationFilter getBeforeAuthenticationFilter() throws Exception {
	        CustomBeforeAuthenticationFilter filter = new CustomBeforeAuthenticationFilter();
	        filter.setAuthenticationManager(authenticationManager());
	        filter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler()
	        		{
	        	 @Override
	        	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	        	            Authentication authentication) throws ServletException, IOException {
	        	         
	        	        // performs custom logics on successful login
 	        		 	String email = request.getParameter("username");
 	        		 	
 	        		 	Employee employee = employeeRepository.findByemail(email);
 	        		 	Login loginInfo = audit.login(employee,request,response);
 	        		 	
 		                //System.out.println("sign in : " +email );
 	        		 	
 	        	        super.onAuthenticationSuccess(request, response, authentication);
	        	    }
	        		});
	        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler() {
	 
	            @Override
	            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	                    AuthenticationException exception) throws IOException, ServletException {
	            	
	            	String email = request.getParameter("username");
	    	       
	                System.out.println("Login error: " + exception.getMessage());
	                super.setDefaultFailureUrl("/signin?error");
	                super.onAuthenticationFailure(request, response, exception);
	            }
	            
	            
	             
	        });
	         
	        return filter;
	    }
	
	 @Bean
	 public LogoutSuccessHandler logoutSuccessHandler() {
	     return new CustomLogoutSuccessHandler();
	 }
	  	
 }
