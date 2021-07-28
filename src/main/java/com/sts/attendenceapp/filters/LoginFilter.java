package com.sts.attendenceapp.filters;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sts.attendenceapp.entities.Employee;
import com.sts.attendenceapp.entities.Login;
import com.sts.attendenceapp.repositories.EmployeeRepository;
import com.sts.attendenceapp.repositories.LoginRepository;

@Component
public class LoginFilter implements Filter  {
	
	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	private Login loginInfo = null;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        /*

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
        	Employee employee = employeeRepository.findByemail(authentication.getName());
        	 	
        	try
        	{
	         loginInfo = loginRepository.findByemployee(authentication.getName());
	     
        	}
        	 catch(Exception e)
        	{
        		 if(loginInfo == null)
             	{
             		Login login = new Login();
                 	login.setBrowser(httpRequest.getHeader("USER-AGENT"));
                 	login.setLoginDate(new Date());
                 	login.setLoginIp(httpRequest.getRemoteAddr());
                 	login.setLastLoginDate(new Date());
                 	login.setLastLoginIp(httpRequest.getRemoteAddr());
                 	login.setEmployee(employee);
                 	loginRepository.save(login);
                 	System.out.println("LoginMaster Details Inserted Successfully");
             	}
             	else
             	{
             		loginInfo.setBrowser(httpRequest.getHeader("USER-AGENT"));
             		loginInfo.setLoginDate(new Date());
             		loginInfo.setLoginIp(httpRequest.getRemoteAddr());
             		loginInfo.setLastLoginDate(new Date());
             		loginInfo.setLastLoginIp(httpRequest.getRemoteAddr());
             		loginInfo.setEmployee(employee);
                 	loginRepository.save(loginInfo);
                 	System.out.println("LoginMaster Details Updated Successfully");
             	}
        	}
             
      
        	System.out.println(httpRequest.getHeader("USER-AGENT"));
        }
        */ 
		
        chain.doFilter(httpRequest,httpResponse);
       
	}
	
}
