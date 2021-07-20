package com.sts.attendenceapp.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.sts.attendenceapp.entities.Mail;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;

	@Value("${app.url}")
	private String baseUrl ;
 	
	public void sendEmail(Mail mail) throws MessagingException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		
		Map<String, Object> model = new HashMap<String, Object>();
        
        model.put("BASE_URL", baseUrl+"resetpassword?token="+mail.getUUID());
        
		Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("ResetPassword.ftl");
		String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model);

	    MimeMessage msg = mailSender.createMimeMessage();	
	    MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
 
	    helper.setTo(mail.getTo());
        helper.setText(htmlBody, true);
        helper.setSubject(mail.getSubject());
 
	    mailSender.send(msg);
	}
	
}
