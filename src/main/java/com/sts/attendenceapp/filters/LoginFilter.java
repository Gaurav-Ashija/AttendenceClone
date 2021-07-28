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
        		
        System.out.println("Hello by Filter");
        
        chain.doFilter(httpRequest,httpResponse);
       
	}
	
}
