/** 
 * @(#)MimeMailServiceTest.java 1.0.0 2011-7-1 下午02:37:15  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.email.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-7-1 下午02:37:15
 */
@DirtiesContext
@ContextConfiguration(locations = { "/test-applicationContext-email.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EmailServiceTest {
	@Autowired
	EmailService emailService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void sendMailTest() {
		try {
			// 发送邮件
			emailService.sendMail("cdzerg@gmail.com", "cicl", "cicl mail test subject", "cicl mail test content");
		} catch (MessagingException e) {
			fail(e.getMessage());
		} 
	}
	
	@Test
	public void sendMailWithAttachTest() {
		try {
			// 发送带附件的邮件
			Resource resource = new ClassPathResource("/ftl/email/mailAttachment.txt");
			String[] attachmentName = { resource.getFile().getName() };
			File[] attachment = { resource.getFile() };

			emailService.sendMail("cdzerg@gmail.com", "cicl", "cicl mail test subject(attachment)", "cicl mail test content",
					attachmentName, attachment);
		} catch (MessagingException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void sendMailFtlTest() {
		try {
			// 发送基于ftl模板的邮件
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("userName", "ciclUser");
			emailService.sendMailFtl("cdzerg@gmail.com", "cicl", "cicl mail test subject(ftl)", "/email/mailTemplate.ftl", model);
		} catch (MessagingException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void sendMailFtlWithAttachTest() {
		try {
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("userName", "ciclUser");

			// 发送基于ftl模板带附件的邮件
			Resource resource = new ClassPathResource("/ftl/email/mailAttachment.txt");
			String[] attachmentName = { resource.getFile().getName() };
			File[] attachment = { resource.getFile() };

			emailService.sendMailFtl("cdzerg@gmail.com", "cicl", "cicl mail test subject(ftl,attachment)", "/email/mailTemplate.ftl", model,
					attachmentName, attachment);
		} catch (MessagingException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
