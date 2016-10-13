package com.cicl.frame.autogen;

import java.io.File;
import java.io.FileFilter;

public class DetaModelFileFilter implements FileFilter
{
	public static final String GLOBAL_DATA_MODEL_NAME = "global.mdl";
	public String fileName;
	
	public DetaModelFileFilter(String fileName){
		this.fileName = fileName;
	}
	public boolean accept(File file) {
		if(this.fileName==null)
		{
			return file.isFile() && !file.getName().matches(GLOBAL_DATA_MODEL_NAME) && file.getName().matches("*(.mdl$)");
		}
		else
		{
			return file.isFile() && file.getName().matches(fileName);
		}
	}
}

