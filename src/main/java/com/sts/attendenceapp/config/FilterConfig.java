package com.sts.attendenceapp.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sts.attendenceapp.filters.LoginFilter;

@Configuration
public class FilterConfig {
	
   /*
	 @Bean
	 public FilterRegistrationBean<LoginFilter> filterRegistrationBean() {
	  FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
	  LoginFilter loginFilter = new LoginFilter();

	  registrationBean.setFilter(loginFilter);
	  registrationBean.addUrlPatterns("/");
	  registrationBean.setOrder(2); //set precedence
	  return registrationBean;
	 }
   */
}
