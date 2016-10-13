/** 
 * @(#)CVSFileUtils.java 1.0.0 2011-5-1 下午06:35:50  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.util;

import java.io.PrintWriter;
import java.util.List;

public class CSVFileUtils {
	
	@SuppressWarnings("unchecked")
	public static void getCSVFile(List list, String[] cols, PrintWriter out) throws Exception {
		// output title
		int i = 1;
		for (String col : cols) {
			out.write(col);
			if (i == cols.length)
				out.write("\r\n");
			else
				out.write(",");
			i++;
		}

		for (Object row : list) {
			int j = 1;
			for (String col : cols) {
				out.write(ReflectionUtils.getFieldValue(row, col).toString());
				if (j == cols.length)
					out.write("\r\n");
				else
					out.write(",");
				j++;
			}
		}

	}
}
