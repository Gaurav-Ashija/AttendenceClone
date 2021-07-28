package com.sts.attendenceapp.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

	  @Override
	    public void onLogoutSuccess(
	      HttpServletRequest request, 
	      HttpServletResponse response, 
	      Authentication authentication) 
	      throws IOException, ServletException {
//		  	it helps in get the information reguading the user information
//		                      CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();

	        String refererUrl = request.getHeader("Referer");
	        System.out.println("The user  is about to logout");

	        super.onLogoutSuccess(request, response, authentication);
	    }
}
