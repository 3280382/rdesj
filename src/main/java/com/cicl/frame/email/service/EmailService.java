/** 
 * @(#)DemoCountry.java 1.0.0 2011-5-11 上午09:50:24  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.email.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Class EmailService
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-7-1 下午06:47:57  
 */
@Service
public class EmailService {

	private static final String DEFAULT_ENCODING = "utf-8";

	private static Logger LOG = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private Configuration freemarkerConfiguration;
	
	/**
	 * 发送单个用户
	 */
	public void sendMail(String to, String from, String subject, String content) throws MessagingException {
		sendMail(to, from, subject, content, null, null);
	}
	
	/**
	 * 发送多用户
	 */
	public void sendMail(String[] to, String from, String subject, String content) throws MessagingException {
		for (int i = 0; i < to.length; i++) {
			sendMail(to[i], from, subject, content);
		}
	}
	
	/**
	 * 发送单个用户，包括附件
	 */
	public void sendMail(String to, String from, String subject, String content, String[] attachmentName,
			File[] attachment) throws MessagingException {
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

		helper.setTo(to);
		helper.setFrom(from);
		helper.setSubject(subject);
		helper.setText(content, true);		

		if (attachmentName != null) {
			for (int i = 0; i < attachmentName.length; i++) {
				helper.addAttachment(attachmentName[i], attachment[i]);
			}
		}
		
		mailSender.send(msg);
		LOG.info("sent mail to:" + to + ",from:" + from + ",subject:" + subject);
	}
	
	/**
	 * 发送多用户，包括附件
	 */
	public void sendMail(String[] to, String from, String subject, String content, String[] attachmentName,
			File[] attachment) throws MessagingException {
		for (int i = 0; i < to.length; i++) {
			sendMail(to[i], from, subject, content, attachmentName, attachment);
		}
	}
	
	/**
	 * 使用ftl模板，发送单个用户
	 */
	@SuppressWarnings("unchecked")
	public void sendMailFtl(String to, String from, String subject, String templateName, Map model)
			throws MessagingException {
		sendMail(to, from, subject, generateContent(templateName, model));
	}
	
	/**
	 * 使用ftl模板，发送多用户
	 */
	@SuppressWarnings("unchecked")
	public void sendMailFtl(String[] to, String from, String subject, String templateName, Map model) throws MessagingException {
		for (int i = 0; i < to.length; i++) {
			sendMailFtl(to[i], from, subject, templateName, model);
		}
	}
	
	/**
	 * 使用ftl模板，发送单个用户，包括附件
	 */
	@SuppressWarnings("unchecked")
	public void sendMailFtl(String to, String from, String subject, String templateName, Map model,
			String[] attachmentName, File[] attachment) throws MessagingException {
		sendMail(to, from, subject, generateContent(templateName, model), attachmentName, attachment);
	}
	
	/**
	 * 使用ftl模板，发送多用户，包括附件
	 */
	@SuppressWarnings("unchecked")
	public void sendMailFtl(String[] to, String from, String subject, String templateName, Map model, String[] attachmentName,
			File[] attachment) throws MessagingException {
		for (int i = 0; i < to.length; i++) {
			sendMailFtl(to[i], from, subject, templateName, model, attachmentName, attachment);
		}
	}

	/**
	 * 使用Freemarker生成html格式内容.
	 */
	@SuppressWarnings("unchecked")
	private String generateContent(String templateName, Map model) throws MessagingException {
		try {
			Template template = freemarkerConfiguration.getTemplate(templateName, DEFAULT_ENCODING);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			LOG.error("Generate mail content fail, freeMarker template isn't exist", e);
			throw new MessagingException("FreeMarker template file isn't exist", e);
		} catch (TemplateException e) {
			LOG.error("Generate mail content fail, freeMarker handle error", e);
			throw new MessagingException("FreeMarker handler error", e);
		}
	}
}
