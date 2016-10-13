package com.cicl.frame.autogen;

import static com.cicl.frame.autogen.Message.*;
import java.io.*;
import java.util.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.cicl.frame.core.util.FileUtils;
import com.cicl.frame.core.util.StringUtils;

import freemarker.template.*;

/**
 * Class AutoGenerator 实现代码自动生成。 通过json格式定义DataModel和Freemarker编写Template文件，
 * 然后遍历生成相关的java、jsp、xml、sql等代码文件。
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-7-25 上午10:31:56
 */
public class AutoGenerator {
	// 代码生成的输入目录,存放DataModel和Template文件
	public String inputPath = null;
	// 代码生成的输出目录(同时也是代码发布的源目录)
	public String outputPath = null;

	public String encoding = "UTF-8";

	public DataModelService dataModelService = null;
	public TemplateService templateService = null;

	public AutoGenerator() {
		dataModelService = new DataModelService();
		templateService = new TemplateService();
	}

	/**
	 * 
	 * 遍历处理inputPath指定的第一层子目录。 第一层子目录下的DataModel文件仅对该目录下的Template文件可见。
	 * 
	 * @throws Exception
	 */
	public void handleFolder() throws Exception {
		if (!setPath())
			return;

		File inputFile = new File(inputPath);
		File[] folders = inputFile.listFiles(new FileFilter() {
			public boolean accept(File file) {
				String last = StringUtils.rightLast(file.getName(), ".");
				return file.isDirectory() && !last.equals("svn");
			}
		});

		if (folders == null) {
			error("在目录找不到文件:" + inputPath);
			return;
		}
		for (File file : folders) {
			handleDataModelAndTemplate(inputPath + file.getName(), outputPath + file.getName());
		}
	}

	private boolean setPath() {
		Resource resource = new ClassPathResource("/autogen");
		String inputPathParent = null, outputPathParent = null;

		try {
			inputPathParent = resource.getFile().getAbsolutePath();
			outputPathParent = resource.getFile().getParentFile().getParentFile().getAbsolutePath();
		} catch (IOException e) {
			error(e);
		}

		// 输入目录
		inputPath = inputPathParent + File.separator + "in" + File.separator;

		// 输出目录，默认在target目录下，以便在eclipse里面可以看到生成的文件
		if (outputPath == null) {
			outputPath = outputPathParent + File.separator + "out" + File.separator;
		}
		
		return true;
	}

	public String getCurrentProjectPath() throws Exception {
		Resource resource = new ClassPathResource("/autogen");

		String path = resource.getFile().getParentFile().getParentFile().getParentFile().getAbsolutePath();
		return path;
	}

	/**
	 * 
	 * 处理某一子目录的DataModel文件和Template文件，读取目录下的DataModel和Template文件进行代码生成。
	 * DataModel文件，以mdl为后缀，以json格式定义数据模型。
	 * Template文件，除mdl后缀和svn目录下的文件，都作为模板文件，以freemarker脚本编写。
	 * 
	 * @param subInputPath
	 * @param subOutputPath
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void handleDataModelAndTemplate(String subInputPath, String subOutputPath) throws Exception {
		info("正在处理文件夹:" + subInputPath);
		Configuration config = templateService.getConfiguration(subInputPath);
		List<File> templateFiles = templateService.getTemplateFiles(subInputPath);
		List<Map> models = dataModelService.getDataModels(subInputPath, dataModelService.getGlobalDataModel(
				subInputPath).get("dataModelFileName").toString());

		if (templateFiles.size() == 0) {
			error("找不到Template文件:" + subInputPath);
			return;
		}
		if (models.size() == 0) {
			error("找不到DataModel文件:" + subInputPath);
			return;
		}

		for (Map model : models) {
			infoHeader("代码自动生成，DataModel文件:" + model.get("objectName"));
			for (File templateFile : templateFiles) {

				String outputFileName = null;
				outputFileName = subOutputPath + File.separator
						+ StringUtils.right(templateFile.getAbsolutePath(), subInputPath);
				// 替换文件路径里面定义的变量
				outputFileName = StringUtils.replace(outputFileName, model, "_--");

				FileUtils.mkdirByFile(outputFileName);
				File outputFile = new File(outputFileName);
				info("  " + outputFile.getName());
				if (!outputFile.exists()) {
					outputFile.createNewFile();
				}
				Writer out = new BufferedWriter(new FileWriter(outputFile));

				config.setDirectoryForTemplateLoading(templateFile.getParentFile());
				Template template = config.getTemplate(templateFile.getName(), encoding);
				template.process(model, out);
			}

			String targetPath = model.get("targetPath").toString().trim();
			if(targetPath.equals("CURRENT_PROJECT")){
				targetPath = getCurrentProjectPath();
			}
			if (!targetPath.equals("")) {
				String overridden = model.get("overridden").toString();
				String add = model.get("add").toString();
				AutoDeployment deployment = new AutoDeployment(overridden, add, outputPath, targetPath, model.get(
						"parentPackage").toString(), model.get("package").toString(), model.get("subpackage")
						.toString());
				deployment.deploy();
			}
		}
	}

	public static void startGen() throws Exception {
		AutoGenerator gener = new AutoGenerator();
		gener.handleFolder();
	}

	public static void main(String[] args) throws Exception {
		startGen();
	}
}
