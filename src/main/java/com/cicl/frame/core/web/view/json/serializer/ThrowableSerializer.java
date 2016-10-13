/** 
 * @(#)ThrowableSerializer.java 1.0.0 2011-5-2 06:20:02  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.view.json.serializer;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * Class ThrowableSerializer 
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-2 06:20:02
 */

public class ThrowableSerializer extends JsonSerializer<Throwable> {
	private static final Log LOG = LogFactory.getLog(ThrowableSerializer.class);

	//@Override
	@Override
	public void serialize(Throwable e, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		LOG.error(e.getMessage(), e);
		jgen.writeString(e.getMessage());
	}

	//@Override
	@Override
	public Class<Throwable> handledType() {
		return Throwable.class;
	}
}