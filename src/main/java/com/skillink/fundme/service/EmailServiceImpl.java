package com.skillink.fundme.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.skillink.fundme.dto.EmailDetails;
import com.skillink.fundme.dto.Mail;
import com.skillink.fundme.util.Util;

@Component
public class EmailServiceImpl {

	@Autowired
	public JavaMailSender emailSender;

	public void sendMail(Mail mail) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();
        message.setContent(mail.getBody(), "text/html");
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

		ArrayList<String> destinationEmail = new ArrayList<String>(Arrays.asList(mail.getMailTo().split(",")));

		if (!Util.isEmptyString(mail.getMailFrom()))
			helper.setFrom(mail.getMailFrom());
		if (!Util.isEmptyString(mail.getMailTo())) {
			if (destinationEmail.size() > 1) {
				helper.setTo(destinationEmail.get(0));
				message.addRecipients(Message.RecipientType.CC, mail.getMailTo());
			} else {
				helper.setTo(mail.getMailTo());
			}
		}

		if (!Util.isEmptyString(mail.getMailSubject()))
			helper.setSubject(mail.getMailSubject());
		if (!Util.isEmptyString(mail.getBody()))
			helper.setText(mail.getBody(), true);

		if (emailSender != null)
			emailSender.send(message);

	}

	@Async
	public void sendMail(EmailDetails email, String toAddress) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();
		message.setContent(email.getContentType(), "text/html");
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

		if (!Util.isEmptyString(email.getMsgSender()))
			helper.setFrom(email.getMsgSender());
		if (!Util.isEmptyString(toAddress))
			helper.setTo(toAddress);
		if (!Util.isEmptyString(email.getMsgSubject()))
			helper.setSubject(email.getMsgSubject());
		if (!Util.isEmptyString(email.getMsgBody()))
			helper.setText(email.getMsgBody(), true);

		if (!Util.isEmptyString(email.getCc()))
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(email.getCc()));

		if (!Util.isEmptyString(email.getBcc()))
			message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(email.getBcc()));

		if (toAddress != null)
			emailSender.send(message);
	}

}