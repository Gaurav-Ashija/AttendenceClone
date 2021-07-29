package com.sts.attendenceapp.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.entities.Login;
import com.sts.attendenceapp.repositories.EmployeeRepository;
import com.sts.attendenceapp.repositories.LoginRepository;
import com.sts.attendenceapp.services.audit.ImplAudit;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private ImplAudit audit;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	  @Override
	    public void onLogoutSuccess(
	      HttpServletRequest request, 
	      HttpServletResponse response, 
	      Authentication authentication) 
	      throws IOException, ServletException {
//		  	it helps in get the information reguading the user information
//		                      CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();

	        String refererUrl = request.getHeader("Referer");
	        
	        String email = authentication.getName();
	        Employee employee = employeeRepository.findByemail(email);
	        
	        List<Login> logins = loginRepository.findByemployee(employee);
	        
	        Login login = null;
	        
	        for(Login log : logins)
	        {
	        	if(log.isLoginActive())
	        	{
	        		login = log;
	        	    break;
	        	}
	        }
	        
 		 	if(login != null)
 		 	{
 		 	Login loginInfo = audit.logout(employee, login, request, response);
 		 	}
 		 	
	        //System.out.println("The user  is about to logout");

	        super.onLogoutSuccess(request, response, authentication);
	    }
}
