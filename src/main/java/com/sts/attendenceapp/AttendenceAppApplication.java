package com.sts.attendenceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;

@SpringBootApplication
public class AttendenceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendenceAppApplication.class, args);
	}
	
 
}
