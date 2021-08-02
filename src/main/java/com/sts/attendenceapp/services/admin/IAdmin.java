package com.sts.attendenceapp.services.admin;

import java.io.IOException;

import javax.mail.MessagingException;

import com.sts.attendenceapp.entities.Employee;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public interface IAdmin {

 	//it will create only one admin in application
	public Employee oneAdmin(Employee employee) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, MessagingException, IOException, TemplateException ;
}
