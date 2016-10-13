/** 
 * @(#)CustomObjectMapper.java 1.0.0 2011-5-5 上午09:06:46  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.view.json.serializer;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.stereotype.Component;


/**
 * Class CustomObjectMapper 
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-5 上午09:06:46
 */
@Component
public class CustomObjectMapper extends ObjectMapper {

	public CustomObjectMapper() {
		SimpleModule module = new SimpleModule(DateSerializer.class.getName(), new Version(1, 0, 0, "FINAL"));
		
		//regist global Date type serializer
		DateSerializer customDateSerializer = new DateSerializer();		
		module.addSerializer(customDateSerializer);
		
		//regist global Timestam type serializer
		TimestampSerializer customTimestampSerializer = new TimestampSerializer();		
		module.addSerializer(customTimestampSerializer);
		
		//regist global Throwable type serializer
		ThrowableSerializer customThrowableSerializer = new ThrowableSerializer();
		module.addSerializer(customThrowableSerializer);
		
		this.registerModule(module);
		
		this.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
	}

}
