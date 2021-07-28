package com.sts.attendenceapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class AttendenceInterceptorConfig extends WebMvcConfigurerAdapter {

	@Autowired
	AttendenceHandleInterceptor attendenceHandleInterceptor;
	
	/*
	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(attendenceHandleInterceptor);
	   } 
	*/   
	
}
