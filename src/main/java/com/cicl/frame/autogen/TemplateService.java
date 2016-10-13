/** 
 * @(#)TemplateService 1.0.0 2011-7-25 上午10:19:47  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.autogen;


import static com.cicl.frame.autogen.Message.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * Class TemplateService
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-7-25 上午10:19:47  
 */
public class TemplateService {

	/**
	 * 
	 * 获取freemarker模板文件的配置
	 *
	 * @param subInputPath
	 * @return
	 * @throws Exception
	 */
	public Configuration getConfiguration(String subInputPath) throws Exception {
		try {
			Configuration config = new Configuration();
			String path = subInputPath;
			config.setDirectoryForTemplateLoading(new File(path));
			config.setObjectWrapper(new DefaultObjectWrapper());

			//BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
			//TemplateHashModel staticModels = wrapper.getStaticModels();
			return config;
		} catch (IOException e) {
			error(e);
			return null;
		}
	}

	/**
	 * 
	 * 获取freemarker模板文件
	 *
	 * @param subInputPath
	 * @return
	 */
	public List<File> getTemplateFiles(String subInputPath) {
		try {
			List<File> temps = new ArrayList<File>();
			File inputFile = new File(subInputPath);

			File[] files = inputFile.listFiles( new TemplateFileFilter(null) );
			for (File file : files) {
				if (file.isFile()) {
					temps.add(file);
				} else {
					List<File> subTemps = getTemplateFiles(file.getAbsolutePath());
					for (File temp : subTemps) {
						temps.add(temp);
					}
				}
			}
			return temps;
		} catch (Exception e) {
			error(e);
			return null;
		}
	}
}
