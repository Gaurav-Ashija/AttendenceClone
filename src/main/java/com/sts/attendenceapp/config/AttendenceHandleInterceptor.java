package com.sts.attendenceapp.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AttendenceHandleInterceptor implements HandlerInterceptor {
	
	   @Override
	   public boolean preHandle(
	      HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	      
		  System.out.println("Pre " + request.getHeader("USER-AGENT")); 
		   
	      return true;
	   }
	   @Override
	   public void postHandle(
	      HttpServletRequest request, HttpServletResponse response, Object handler, 
	      ModelAndView modelAndView) throws Exception {
		   
		   System.out.println("Post " + request.getHeader("USER-AGENT")); 
	   }
	   
	   @Override
	   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
	      Object handler, Exception exception) throws Exception {
		   
		   System.out.println("Comp " + request.getHeader("USER-AGENT")); 
	   }

}
