package com.cicl.frame.autogen;

import java.io.File;
import java.io.FileFilter;

import com.cicl.frame.core.util.StringUtils;

public class TemplateFileFilter implements FileFilter
{
	public String fileName;
	
	public TemplateFileFilter(String fileName){
		this.fileName = fileName;
	}
	public boolean accept(File file) {
		if(this.fileName==null)
		{
			String suffix = StringUtils.rightLast(file.getName(), ".");
			return !suffix.equals("mdl") && !suffix.equals("svn");
		}
		else
		{
			return file.isFile() && file.getName().matches(fileName);
		}
	}
}

