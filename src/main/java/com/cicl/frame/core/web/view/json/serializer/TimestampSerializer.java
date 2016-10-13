/** 
 * @(#)DateSerializer.java 1.0.0 2011-5-2 06:20:02  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.view.json.serializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.cicl.frame.core.util.date.DateConstant;

/**
 * Class DateSerializer 
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-2 06:20:02
 */

public class TimestampSerializer extends JsonSerializer<Timestamp> {

	//@Override
	public void serialize(Timestamp value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat(DateConstant.DATE_TIME_FORMAT);
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
	}

	//@Override
	public Class<Timestamp> handledType() {
		return Timestamp.class;
	}
}