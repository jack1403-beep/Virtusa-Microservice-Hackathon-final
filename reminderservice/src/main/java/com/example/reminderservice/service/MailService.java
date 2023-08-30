package com.example.reminderservice.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.reminderservice.modeldto.Mail;



@Service
public class MailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(Mail mail) 
	{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(mail.getMailSubject());
			mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
			mimeMessageHelper.setTo(mail.getMailTo());
			mimeMessageHelper.setText(mail.getMailContent(),true);//true so that we can use html tag inside content of mail
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}