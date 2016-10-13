package com.cicl.frame.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLDecoder;
import java.util.TimeZone;

public class FileUtils {
	public static final String EXT_FILE_JAR = "jar";
	public static final String FLAG_UPLOAD = "UL";
	private static final int TIMEZONE_RAWOFFSET = TimeZone.getDefault().getRawOffset();

	public static boolean fileExists(String _sPathFileName) {
		File file = new File(_sPathFileName);
		return file.exists();
	}

	public static boolean pathExists(String _sPathFileName) {
		String sPath = extractFilePath(_sPathFileName);
		return fileExists(sPath);
	}

	public static String extractFileName(String _sFilePathName) {
		return extractFileName(_sFilePathName, File.separator);
	}

	public static String extractFileName(String _sFilePathName, String _sFileSeparator) {
		int nPos = _sFilePathName.lastIndexOf(_sFileSeparator);
		return _sFilePathName.substring(nPos + 1);
	}

	public static String extractHttpFileName(String _sFilePathName) {
		int nPos = _sFilePathName.lastIndexOf("/");
		return _sFilePathName.substring(nPos + 1);
	}

	public static String extractFileExt(String _sFilePathName) {
		int nPos = _sFilePathName.lastIndexOf('.');
		return nPos < 0 ? "" : _sFilePathName.substring(nPos + 1);
	}

	public static String extractFilePath(String _sFilePathName) {
		int nPos = _sFilePathName.lastIndexOf('/');
		if (nPos < 0)
			nPos = _sFilePathName.lastIndexOf('\\');
		return nPos < 0 ? "" : _sFilePathName.substring(0, nPos + 1);
	}

	public static String toAbsolutePathName(String _sFilePathName) {
		File file = new File(_sFilePathName);
		return file.getAbsolutePath();
	}

	public static String extractFileDrive(String _sFilePathName) {
		int nLen = _sFilePathName.length();
		if (nLen > 2 && _sFilePathName.charAt(1) == ':')
			return _sFilePathName.substring(0, 2);
		if (nLen > 2 && _sFilePathName.charAt(0) == File.separatorChar
				&& _sFilePathName.charAt(1) == File.separatorChar) {
			int nPos = _sFilePathName.indexOf(File.separatorChar, 2);
			if (nPos >= 0)
				nPos = _sFilePathName.indexOf(File.separatorChar, nPos + 1);
			return nPos < 0 ? _sFilePathName : _sFilePathName.substring(0, nPos);
		} else {
			return "";
		}
	}

	public static boolean deleteFile(String _sFilePathName) {
		File file = new File(_sFilePathName);
		return file.exists() ? file.delete() : false;
	}

	public static boolean makeDir(String _sDir, boolean _bCreateParentDir) {
		File file = new File(_sDir);
		if (_bCreateParentDir)
			return file.mkdirs();
		else
			return file.mkdir();
	}
	
	public static String toFilePath(String ... names){
		String path = "";
		
		for(String name : names){
			path += name + File.separator;
		}
		
		return path;
	}

	public static boolean deleteDir(String _sPath) {
		File path = new File(_sPath);
		return deleteDir(path);
	}

	public static boolean deleteDir(File _path) {
		if (!_path.exists())
			return false;
		if (_path.isDirectory()) {
			File files[] = _path.listFiles();
			for (int i = 0; i < files.length; i++) {
				File aFile = files[i];
				if (aFile.isDirectory())
					deleteDir(aFile);
				else
					aFile.delete();
			}

		}
		return _path.delete();
	}

	public static String mapResouceFullPath(String _resource) throws Exception {
		URL url = Thread.currentThread().getContextClassLoader().getResource(_resource);
		if (url == null)
			throw new Exception("File[" + _resource + "] not found！");
		String sPath = null;
		try {
			sPath = url.getFile();
			if (sPath.indexOf('%') >= 0)
				sPath = URLDecoder.decode(url.getFile(), null);
		} catch (Exception ex) {
			throw new Exception("File[" + url.getFile() + "] conversion failed！", ex);
		}
		return sPath;
	}

	public static String mapResouceFullPath(String _resource, Class _currClass) throws Exception {
		URL url = _currClass.getResource(_resource);
		if (url == null)
			throw new Exception("File [" + _resource + "] not found！");
		String sPath = null;
		try {
			sPath = url.getFile();
			if (sPath.indexOf('%') >= 0)
				sPath = URLDecoder.decode(url.getFile(), null);
		} catch (Exception ex) {
			throw new Exception("File [" + url.getFile() + "]conversion failed！", ex);
		}
		return sPath;
	}

	public static String replace2UnixSeperator(String path) {
		char[] cs = path.toCharArray();
		char[] cr = new char[cs.length];
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == '\\')
				cr[i] = '/';
			else
				cr[i] = cs[i];
		}
		return new String(cr);
	}

	public static String replace2WinSeperator(String path) {
		char[] cs = path.toCharArray();
		char[] cr = new char[cs.length];
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == '/')
				cs[i] = '\\';
			else
				cr[i] = cs[i];
		}
		return new String(cr);
	}

	public static boolean writeFile(String _sFileName, String _sFileContent) throws Exception {
		return writeFile(_sFileName, _sFileContent, StringUtils.ENCODING_DEFAULT);
	}

	public static boolean writeFile(String _sFileName, String _sFileContent, String _encoding) throws Exception {
		return writeFile(_sFileName, _sFileContent, _encoding, false);
	}

	public static boolean writeFile(String _sFileName, String _sFileContent, String _sFileEncoding,
			boolean _bWriteUnicodeFlag) throws Exception {
		boolean bRet;
		String sPath = extractFilePath(_sFileName);
		if (!pathExists(sPath))
			makeDir(sPath, true);
		String sFileEncoding = StringUtils.showNull(_sFileEncoding, StringUtils.ENCODING_DEFAULT);
		bRet = false;
		FileOutputStream fos = null;
		Writer outWriter = null;
		try {
			fos = new FileOutputStream(_sFileName);
			outWriter = new OutputStreamWriter(fos, sFileEncoding);
			if (_bWriteUnicodeFlag)
				outWriter.write(65279);
			outWriter.write(_sFileContent);
			bRet = true;
		} catch (Exception ex) {
			throw new Exception("Write file failed(FileUtils.writeFile)", ex);
		} finally {
			try {
				if (outWriter != null) {
					outWriter.flush();
					outWriter.close();
				}
				if (fos != null)
					fos.close();
			} catch (Exception ex) {
			}
		}
		return bRet;
	}

	public static boolean isJarFile(String filePath) {
		if (filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase().indexOf(FileUtils.EXT_FILE_JAR) > -1) {
			return true;
		} else {
			return false;
		}
	}

	public static synchronized String getNextFileName(String sFileExt) {
		DateTimeUtils crTime = new DateTimeUtils();
		String sDate = crTime.toString("yyyyMMdd");

		long lTime = (crTime.getTimeInMillis() + TIMEZONE_RAWOFFSET) % 86400000;
		String sTime = StringUtils.numberToStr(lTime, 8, '0');
		String sRandom = StringUtils.numberToStr(Math.round(Math.random() * 10000.0), 4, '0');
		String fileExt = sFileExt.trim();
		if (sFileExt.length() > 0 && sFileExt.charAt(0) != '.')
			sFileExt = "." + sFileExt;
		String sFileName = sDate + sTime + sRandom;
		for (int i = 0; i < 2; i++) {
			if (i > 0) {
				sFileName = sFileName + StringUtils.numberToStr(Math.round(Math.random() * 100.0), 2, '0');
			}
		}

		return sFileName;
	}

	public static synchronized String getNextFileName(String sFileDir, String sPathFlag, String sFileExt,
			boolean bIncludePath) throws Exception {
		String nextFileName = null;
		DateTimeUtils crTime = new DateTimeUtils();
		String sDate = crTime.toString("yyyyMMdd");
		String sFilePath = sFileDir + sPathFlag + sDate.substring(0, 6) + File.separatorChar + sPathFlag
				+ sDate.substring(0, 8) + File.separatorChar;

		if (!FileUtils.fileExists(sFilePath)) {
			try {
				FileUtils.makeDir(sFilePath, true);
			} catch (Exception ex) {
				throw new Exception("The path" + sFilePath + "can not be created(FileUtils.getNextFileName)");
			}
		}
		long lTime = (crTime.getTimeInMillis() + TIMEZONE_RAWOFFSET) % 86400000;
		String sTime = StringUtils.numberToStr(lTime, 8, '0');
		String sRandom = StringUtils.numberToStr(Math.round(Math.random() * 10000.0), 4, '0');
		String fileExt = sFileExt.trim();
		if (sFileExt.length() > 0 && sFileExt.charAt(0) != '.')
			sFileExt = "." + sFileExt;
		String sFileName = sPathFlag + sDate + sTime + sRandom;
		for (int i = 0; i < 2; i++) {
			if (i > 0) {
				sFileName = sFileName + StringUtils.numberToStr(Math.round(Math.random() * 100.0), 2, '0');
			}
			if (!FileUtils.fileExists(sFilePath + sFileName + sFileExt)) {
				nextFileName = (bIncludePath ? sFilePath : "") + sFileName + sFileExt;
			}
		}
		return nextFileName;
	}

	public static void mkdirByFile(String path) {
		File fd = null;
		try {
			fd = new File(path);
			if (!fd.getParentFile().exists()) {
				fd.getParentFile().mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fd = null;
		}
	}

	public static void copyFile(String oldPath, String newPath) throws Exception {
		InputStream in = null;
		FileOutputStream out = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				in = new FileInputStream(oldPath); //读入原文件

				mkdirByFile(newPath);
				out = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = in.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小
					out.write(buffer, 0, byteread);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
		}
	}

	public static String getAllLines(File file, String encoding) throws Exception {
		return getAllLines(file, encoding, 20240);
	}

	public static String getAllLines(File file, String encoding, int filelen) throws Exception {
		byte[] buf = new byte[filelen];
		int len = 0;
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			len = fin.read(buf);

			return new String(buf, 0, len, encoding);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (fin != null)
				try {
					fin.close();
				} catch (IOException e) {
				}
		}
	}

}
