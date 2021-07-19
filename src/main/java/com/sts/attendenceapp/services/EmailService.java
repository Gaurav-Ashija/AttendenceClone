package com.sts.attendenceapp.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toEmail,String subject,String body) throws MessagingException
	{
	    MimeMessage msg = mailSender.createMimeMessage();	
	    MimeMessageHelper helper = new MimeMessageHelper(msg, true);
	    helper.setTo(toEmail);
	    helper.setSubject(subject);
	    helper.setText(body, true);
	    mailSender.send(msg);
	}
	
}
