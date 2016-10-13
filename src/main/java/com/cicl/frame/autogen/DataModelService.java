/** 
 * @(#)DataModelService.java 1.0.0 2011-7-25 上午10:19:47  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.autogen;

import static com.cicl.frame.autogen.Message.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.cicl.frame.core.util.FileUtils;

/**
 * Class DataModelService
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-7-25 上午10:19:47  
 */
public class DataModelService {
	public String encoding = "UTF-8";
	
	/**
	 * 
	 * 获取ObjectMapper，设置JSON反序列化参数
	 *
	 * @return
	 */
	private ObjectMapper getObjectMapper(){
		ObjectMapper objectMapper;
		objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		return objectMapper;
	}

	/**
	 * 
	 * 从指定的目录获取DataModel文件，以mdl为后缀
	 *
	 * @param subInputPath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getDataModels(String subInputPath, String dataModelFileNamePattern){
		List<Map> models = new ArrayList<Map>();

		File inputFile = new File(subInputPath);
		File[] files = inputFile.listFiles(new DetaModelFileFilter(dataModelFileNamePattern));
		
		ObjectMapper objectMapper = getObjectMapper();
		
		Map globalModel = getGlobalDataModel(subInputPath);
		
		for (File file : files) {
			Map model = null;
			try {
				model = objectMapper.readValue(FileUtils.getAllLines(file, encoding), HashMap.class);
				mergeDataModel(model, globalModel);				
				
			} catch (JsonParseException e) {
				error(e);
			} catch (JsonMappingException e) {
				error(e);
			} catch (IOException e) {
				error(e);
			} catch (Exception e) {
				error(e);
			}

			models.add(model);
		}
		return models;
	}
	
	
	/**
	 * 
	 * 从指定的目录获取全局的DataModel文件，该文件定义的值将合并到同一目录下其它的DataModel文件
	 *
	 * @param subInputPath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getGlobalDataModel(String subInputPath){
		String filePath = subInputPath + File.separator + DetaModelFileFilter.GLOBAL_DATA_MODEL_NAME;
		File file = new File(filePath);
		
		ObjectMapper objectMapper = getObjectMapper();
		if(file.exists()){
			Map model = null;
			try {
				model = objectMapper.readValue(FileUtils.getAllLines(file, encoding), HashMap.class);
			} catch (JsonParseException e) {
				error(e);
			} catch (JsonMappingException e) {
				error(e);
			} catch (IOException e) {
				error(e);
			} catch (Exception e) {
				error(e);
			}

			return model;
		}
		else{
			warn("没有发现"+DetaModelFileFilter.GLOBAL_DATA_MODEL_NAME+"文件:" + filePath );
			return null;
		}
	}	
	
	
	/**
	 * 把srcModel里的所有值放到dstModel
	 *
	 * @param dstModel
	 * @param srcModel
	 */
	@SuppressWarnings("unchecked")
	private void mergeDataModel(Map dstModel, Map srcModel){
		if(srcModel==null || dstModel==null) return;
		dstModel.putAll(srcModel);
	}

}
