/** 
 * @(#DemoAjaxController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.system.web.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cicl.frame.core.web.controller.AbstractJsonController;

/**
 * Class UploadController
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 09:05:03
 */

@Controller
public class UploadController extends AbstractJsonController {
	private static final Log LOG = LogFactory.getLog(UploadController.class);
	public static final String UPLOAD_OK = "/upload/uploadOk";
	public static final String UPLOAD_ERROR = "/upload/uploadError";

	public UploadController() {
		super();
	}

	@RequestMapping
	public String showUpload(String action, Model model) {
		model.addAttribute("action", action);
		// Return to view
		return null;
	}

	/*
	 * @RequestMapping public String upload(@RequestParam("file") MultipartFile
	 * file, String name) throws Exception { if (!file.isEmpty()) { // just demo
	 * get bytes byte[] bytes = file.getBytes();
	 * LOG.info("user input file name:" + name); LOG.info(file.getName() +
	 * ", len:" + bytes.length);
	 * 
	 * // store the bytes somewhere // Return ok page return UPLOAD_OK; } else {
	 * return UPLOAD_ERROR; } }
	 */
	@RequestMapping
	public String upload(MultipartHttpServletRequest request, String[] names) {		
		try {
			List<MultipartFile> files = request.getFiles("file");
			
			int i=0;
			for (MultipartFile file : files) {
				// just demo get bytes
				byte[] bytes = file.getBytes();
				
				// store the bytes somewhere
				LOG.info("user input file name:" + names[i] +"," + file.getName());
				LOG.info(file.getName() + ", len:" + bytes.length);
				
				i++;
			}
		} 
		catch (IOException e) {
			LOG.error(e);
		}
		
		// Return ok page
		return UPLOAD_OK;
	}
}
