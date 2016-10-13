/** 
 * @(#)AutoDeployment.java 1.0.0 2011-6-10 上午01:06:57  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.autogen;

import static com.cicl.frame.autogen.Message.*;

import java.io.File;

import com.cicl.frame.core.util.FileUtils;

/**
 * Class AutoDeployment
 * 把AutoGenerator自动生成的代码复制到目标目录。
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-6-10 上午01:06:57  
 */
public class AutoDeployment {	
	
	/**进行代码发布时，文件的覆盖方式，TRUE|FALSE|TIP
	* TRUE:如果文件存在，不提示用户选择，直接覆盖；
	* FALSE:如果文件存在，不提示用户选择，直接跳过不覆盖；
	* TIP:如果文件存在，提示用户选择是否覆盖；
	*/
	private String overridden = DEPLOY_TIP;
	
	/**进行代码发布时，文件的新增方式，TRUE|FALSE|TIP
	* TRUE:如果文件存在，不提示用户选择，直接新增；
	* FALSE:如果文件存在，不提示用户选择，直接跳过不新增；
	* TIP:如果文件不存在，提示用户是否选择新增；
	*/
	private String add = DEPLOY_TIP;
	
	// 代码生成的输出目录
	public String outputPath = null;
	// 代码生成的发布目录
	public String targetPath = null;
	// 代码生成的发布目录上级package名称
	public String parentPackage = null;
	// 代码生成的发布目录第一级package名称
	public String packageName = null;
	// 代码生成的发布目录子package名称
	public String subpackageName = null;
	
	public static final String DEPLOY_TRUE = "TRUE";
	public static final String DEPLOY_FALSE = "FALSE";
	public static final String DEPLOY_TIP = "TIP";

	public AutoDeployment(String overridden, String add, String outputPath, String targetPath, String parentPackage,
			String packageName, String subpackageName) {
		super();
		this.overridden = overridden;
		this.add = add;
		this.outputPath = outputPath;
		this.targetPath = targetPath;
		this.parentPackage = parentPackage;
		this.packageName = packageName;
		this.subpackageName = subpackageName;
	}

	public void deploy() throws Exception{
		infoHeader("代码自动发布，目标目录:" + FileUtils.toFilePath(targetPath,packageNameToFolderPath(parentPackage), packageName, subpackageName));
		
		copyFolder(
				FileUtils.toFilePath(outputPath, "cicl","java",packageName, subpackageName), 
				FileUtils.toFilePath(targetPath, "src","main","java",packageNameToFolderPath(parentPackage), packageName, subpackageName)
		);
		
		copyFolder(
				FileUtils.toFilePath(outputPath,"cicl","resources", packageName, subpackageName), 
				FileUtils.toFilePath(targetPath,"src","main","resources","message","vt","jsp_ori",packageName)
		);
		
		copyFolder(
				FileUtils.toFilePath(outputPath,"cicl","webapp",packageName,subpackageName),
				FileUtils.toFilePath(targetPath,"src","main","webapp","WEB-INF","vt","jsp", packageName, subpackageName)
		);
	
	}
	
	/**
	 * 
	 * 输入java格式的package名称"com.cicl.frame"，返回路径名称"com/cicl/frame"
	 *
	 * @param packageName
	 * @return
	 */
	private String packageNameToFolderPath(String packageName){
		if(packageName==null) return null;
		return packageName.replaceAll("\\.", "\\"+File.separator);
	}
	
	public void copyFolder(String srcFolderPath, String targetFolderPath) throws Exception{
		File srcFolder = new File(srcFolderPath);
		info("");
		info( "COPYFOLDER src=" + srcFolderPath );
		info( "            to=" + targetFolderPath );
		
		for(File file : srcFolder.listFiles()){
			String targetFilePath = targetFolderPath + File.separator + file.getName();
			File targetFile = new File(targetFilePath);
			
			String srcShortName = file.getName();
			String targetShortName = targetFile.getName();

			if(file.isFile()){
				if( targetFile.exists() ){
					if( overridden.equals( DEPLOY_TRUE ) ) {
						info("  COPYFILE(overridden) "+targetShortName);
						FileUtils.copyFile(file.getAbsolutePath(),  targetFilePath);
					}
					else if( overridden.equals( DEPLOY_FALSE ) ) {
						info("  COPYFILE(ignore overridden) "+targetShortName);
					}
					else if( overridden.equals( DEPLOY_TIP ) ) {
						char input = readChar(srcShortName+"文件已经存在，请选择是否覆盖，y=覆盖，n=不覆盖，q=退出程序","y,n,q");
						
						if(input == 'y'){
							info("  COPYFILE(overridden) "+targetShortName);
							FileUtils.copyFile(file.getAbsolutePath(),  targetFilePath);
						}
						else if(input == 'n'){
							info("  COPYFILE(ignore overridden) "+targetShortName);
						}
						else if(input == 'q'){
							System.exit(0);
						}
					}
				}
				else{					
					if( add.equals( DEPLOY_TRUE ) ) {
						info("  COPYFILE(add) "+targetShortName);
						FileUtils.copyFile(file.getAbsolutePath(),  targetFilePath);
					}
					else if( add.equals( DEPLOY_FALSE ) ) {
						info("  COPYFILE(ignore add) "+targetShortName);
					}
					else if( add.equals( DEPLOY_TIP ) ) {
						char input = readChar(srcShortName+"文件不存在，请选择是否新增，y=新增，n=不新增，q=退出程序","y,n,q");
						if(input == 'y'){
							info("  COPYFILE(add) "+targetShortName);
							FileUtils.copyFile(file.getAbsolutePath(),  targetFilePath);
						}
						else if(input == 'n'){
							info("  COPYFILE(ignore add) "+targetShortName);
						}
						else if(input == 'q'){
							System.exit(0);
						}
					}
				}				
			}
			else if(file.isDirectory()){							
				if( !targetFile.exists() ){					
					info("MKDIR:"+targetFilePath);
					if( !targetFile.mkdirs() ){
						error("Make dir error:" + file.getAbsolutePath());
					}
				}
				
				copyFolder(file.getAbsolutePath() , targetFilePath);
			}
		}		
	}	
}
