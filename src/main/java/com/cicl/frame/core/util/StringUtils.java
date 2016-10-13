package com.cicl.frame.core.util;

import java.io.*;
import java.util.*;

/**
 * Class StringUtils TODO Purpose/description of class
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2008-4-24 上午10:53:56
 */
public class StringUtils {
	public static String ENCODING_DEFAULT = "UTF-8";
	public static String GET_ENCODING_DEFAULT = "UTF-8";
	public static String FILE_WRITING_ENCODING = "GBK";
	private static final String CDATA_END = "]]>";

	// 获取中文拼音
	// 国标码和区位码转换常量
	private static final int GB_SP_DIFF = 160;

	// 存放国标一级汉字不同读音的起始区位码
	private static final int[] secPosvalueList = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212,
			3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };

	// 存放国标一级汉字不同读音的起始区位码对应读音
	private static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z' };

	public StringUtils() {
	}

	// 获取一个字符串的拼音码
	public static String getLetter(String oriStr) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;

		// 依次处理str中每个字符
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();

			// 每个字符如果非汉字
			if (uniCode[0] < 128 && uniCode[0] > 0) {
				buffer.append(temp);
			} else {
				// 汉字将转换
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	// 获取一个字符串的首字母拼音码
	public static String getFirstLetter(String oriStr) {
		return String.valueOf(getLetter(oriStr).charAt(0));
	}

	/**
	 * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
	 * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
	 * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
	 */
	private static char convert(byte[] bytes) {

		char result = '-';
		int secPosvalue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}
		secPosvalue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosvalue >= secPosvalueList[i] && secPosvalue < secPosvalueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	//
	public static boolean isEmpty(String _string) {
		return _string == null || _string.trim().length() == 0;
	}

	public static String showObjNull(Object p_sValue) {
		return showObjNull(p_sValue, "");
	}

	public static String showObjNull(Object _sValue, String _sReplaceIfNull) {
		if (_sValue == null)
			return _sReplaceIfNull;
		else
			return _sValue.toString();
	}

	public static String showNull(String p_sValue) {
		return showNull(p_sValue, "");
	}

	public static String showNull(String _sValue, String _sReplaceIfNull) {
		return _sValue != null ? _sValue : _sReplaceIfNull;
	}

	public static String expandStr(String _string, int _length, char _chrFill, boolean _bFillOnLeft) {
		int nLen = _string.length();
		if (_length <= nLen)
			return _string;
		String sRet = _string;
		for (int i = 0; i < _length - nLen; i++)
			sRet = _bFillOnLeft ? _chrFill + sRet : sRet + _chrFill;

		return sRet;
	}

	public static String setStrEndWith(String _string, char _chrEnd) {
		if (_string == null)
			return null;
		if (_string.charAt(_string.length() - 1) != _chrEnd)
			return _string + _chrEnd;
		else
			return _string;
	}

	public static String makeBlanks(int _length) {
		if (_length < 1)
			return "";
		StringBuffer buffer = new StringBuffer(_length);
		for (int i = 0; i < _length; i++)
			buffer.append(' ');

		return buffer.toString();
	}

	public static String replaceStr(String _strSrc, String _strOld, String _strNew) {
		if (_strSrc == null || _strNew == null || _strOld == null)
			return _strSrc;
		char srcBuff[] = _strSrc.toCharArray();
		int nSrcLen = srcBuff.length;
		if (nSrcLen == 0)
			return "";
		char oldStrBuff[] = _strOld.toCharArray();
		int nOldStrLen = oldStrBuff.length;
		if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
			return _strSrc;
		StringBuffer retBuff = new StringBuffer(nSrcLen * (1 + _strNew.length() / nOldStrLen));
		boolean bIsFound = false;
		for (int i = 0; i < nSrcLen;) {
			bIsFound = false;
			if (srcBuff[i] == oldStrBuff[0]) {
				int j;
				for (j = 1; j < nOldStrLen; j++)
					if (i + j >= nSrcLen || srcBuff[i + j] != oldStrBuff[j])
						break;

				bIsFound = j == nOldStrLen;
			}
			if (bIsFound) {
				retBuff.append(_strNew);
				i += nOldStrLen;
			} else {
				int nSkipTo;
				if (i + nOldStrLen >= nSrcLen)
					nSkipTo = nSrcLen - 1;
				else
					nSkipTo = i;
				for (; i <= nSkipTo; i++)
					retBuff.append(srcBuff[i]);

			}
		}

		srcBuff = (char[]) null;
		oldStrBuff = (char[]) null;
		return retBuff.toString();
	}

	public static String replaceStr(StringBuffer _strSrc, String _strOld, String _strNew) {
		if (_strSrc == null)
			return null;
		int nSrcLen = _strSrc.length();
		if (nSrcLen == 0)
			return "";
		char oldStrBuff[] = _strOld.toCharArray();
		int nOldStrLen = oldStrBuff.length;
		if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
			return _strSrc.toString();
		StringBuffer retBuff = new StringBuffer(nSrcLen * (1 + _strNew.length() / nOldStrLen));
		boolean bIsFound = false;
		for (int i = 0; i < nSrcLen;) {
			bIsFound = false;
			if (_strSrc.charAt(i) == oldStrBuff[0]) {
				int j;
				for (j = 1; j < nOldStrLen; j++)
					if (i + j >= nSrcLen || _strSrc.charAt(i + j) != oldStrBuff[j])
						break;

				bIsFound = j == nOldStrLen;
			}
			if (bIsFound) {
				retBuff.append(_strNew);
				i += nOldStrLen;
			} else {
				int nSkipTo;
				if (i + nOldStrLen >= nSrcLen)
					nSkipTo = nSrcLen - 1;
				else
					nSkipTo = i;
				for (; i <= nSkipTo; i++)
					retBuff.append(_strSrc.charAt(i));

			}
		}

		oldStrBuff = (char[]) null;
		return retBuff.toString();
	}

	public static String getStr(String _strSrc) {
		return getStr(_strSrc, ENCODING_DEFAULT);
	}

	public static String getStr(String _strSrc, boolean _bPostMethod) {
		return getStr(_strSrc, _bPostMethod ? ENCODING_DEFAULT : GET_ENCODING_DEFAULT);
	}

	public static String getStr(String _strSrc, String _encoding) {
		if (_encoding == null || _encoding.length() == 0)
			return _strSrc;
		try {
			byte byteStr[] = new byte[_strSrc.length()];
			char charStr[] = _strSrc.toCharArray();
			for (int i = byteStr.length - 1; i >= 0; i--)
				byteStr[i] = (byte) charStr[i];

			return new String(byteStr, _encoding);
		} catch (Exception ex) {
			return _strSrc;
		}
	}

	public static String toISO_8859(String _strSrc) {
		if (_strSrc == null)
			return null;
		try {
			return new String(_strSrc.getBytes(), "ISO-8859-1");
		} catch (Exception ex) {
			return _strSrc;
		}
	}

	public static byte[] getUTF8Bytes(String _string) {
		char c[] = _string.toCharArray();
		int len = c.length;
		int count = 0;
		for (int i = 0; i < len; i++) {
			int ch = c[i];
			if (ch <= 127)
				count++;
			else if (ch <= 2047)
				count += 2;
			else
				count += 3;
		}

		byte b[] = new byte[count];
		int off = 0;
		for (int i = 0; i < len; i++) {
			int ch = c[i];
			if (ch <= 127)
				b[off++] = (byte) ch;
			else if (ch <= 2047) {
				b[off++] = (byte) (ch >> 6 | 0xc0);
				b[off++] = (byte) (ch & 0x3f | 0x80);
			} else {
				b[off++] = (byte) (ch >> 12 | 0xe0);
				b[off++] = (byte) (ch >> 6 & 0x3f | 0x80);
				b[off++] = (byte) (ch & 0x3f | 0x80);
			}
		}

		return b;
	}

	public static String getUTF8String(byte b[]) {
		return getUTF8String(b, 0, b.length);
	}

	public static String getUTF8String(byte b[], int off, int len) {
		int count = 0;
		int max = off + len;
		int i;
		for (i = off; i < max;) {
			int c = b[i++] & 0xff;
			switch (c >> 4) {
			case 0: // '\0'
			case 1: // '\001'
			case 2: // '\002'
			case 3: // '\003'
			case 4: // '\004'
			case 5: // '\005'
			case 6: // '\006'
			case 7: // '\007'
				count++;
				break;

			case 12: // '\f'
			case 13: // '\r'
				if ((b[i++] & 0xc0) != 128)
					throw new IllegalArgumentException();
				count++;
				break;

			case 14: // '\016'
				if ((b[i++] & 0xc0) != 128 || (b[i++] & 0xc0) != 128)
					throw new IllegalArgumentException();
				count++;
				break;

			case 8: // '\b'
			case 9: // '\t'
			case 10: // '\n'
			case 11: // '\013'
			default:
				throw new IllegalArgumentException();
			}
		}

		if (i != max)
			throw new IllegalArgumentException();
		char cs[] = new char[count];
		i = 0;
		while (off < max) {
			int c = b[off++] & 0xff;
			switch (c >> 4) {
			case 0: // '\0'
			case 1: // '\001'
			case 2: // '\002'
			case 3: // '\003'
			case 4: // '\004'
			case 5: // '\005'
			case 6: // '\006'
			case 7: // '\007'
				cs[i++] = (char) c;
				break;

			case 12: // '\f'
			case 13: // '\r'
				cs[i++] = (char) ((c & 0x1f) << 6 | b[off++] & 0x3f);
				break;

			case 14: // '\016'
				int t = (b[off++] & 0x3f) << 6;
				cs[i++] = (char) ((c & 0xf) << 12 | t | b[off++] & 0x3f);
				break;

			case 8: // '\b'
			case 9: // '\t'
			case 10: // '\n'
			case 11: // '\013'
			default:
				throw new IllegalArgumentException();
			}
		}
		return new String(cs, 0, count);
	}

	public static String byteToHexString(byte _bytes[]) {
		return byteToHexString(_bytes, ',');
	}

	public static String byteToHexString(byte _bytes[], char _delim) {
		String sRet = "";
		for (int i = 0; i < _bytes.length; i++) {
			if (i > 0)
				sRet = sRet + _delim;
			sRet = sRet + Integer.toHexString(_bytes[i]);
		}

		return sRet;
	}

	public static String byteToString(byte _bytes[], char _delim, int _radix) {
		String sRet = "";
		for (int i = 0; i < _bytes.length; i++) {
			if (i > 0)
				sRet = sRet + _delim;
			sRet = sRet + Integer.toString(_bytes[i], _radix);
		}

		return sRet;
	}

	public static String transDisplay(String _sContent) {
		return transDisplay(_sContent, true);
	}

	public static String transDisplay(String _sContent, boolean _bChangeBlank) {
		if (_sContent == null)
			return "";
		char srcBuff[] = _sContent.toCharArray();
		int nSrcLen = srcBuff.length;
		StringBuffer retBuff = new StringBuffer(nSrcLen * 2);
		for (int i = 0; i < nSrcLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case 32: // ' '
				retBuff.append(_bChangeBlank ? "&nbsp;" : " ");
				break;

			case 60: // '<'
				retBuff.append("&lt;");
				break;

			case 62: // '>'
				retBuff.append("&gt;");
				break;

			case 10: // '\n'
				retBuff.append("<br>");
				break;

			case 34: // '"'
				retBuff.append("&quot;");
				break;

			case 38: // '&'
				boolean bUnicode = false;
				for (int j = i + 1; j < nSrcLen && !bUnicode; j++) {
					cTemp = srcBuff[j];
					if (cTemp == '#' || cTemp == ';') {
						retBuff.append("&");
						bUnicode = true;
					}
				}

				if (!bUnicode)
					retBuff.append("&amp;");
				break;

			case 9: // '\t'
				retBuff.append(_bChangeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;" : "    ");
				break;

			default:
				retBuff.append(cTemp);
				break;
			}
		}

		return retBuff.toString();
	}

	public static String transDisplay_bbs(String _sContent, String p_sQuoteColor) {
		return transDisplay_bbs(_sContent, p_sQuoteColor, true);
	}

	public static String transDisplay_bbs(String _sContent, String p_sQuoteColor, boolean _bChangeBlank) {
		if (_sContent == null)
			return "";
		boolean bIsQuote = false;
		boolean bIsNewLine = true;
		char srcBuff[] = _sContent.toCharArray();
		int nSrcLen = srcBuff.length;
		StringBuffer retBuff = new StringBuffer((int) ((double) nSrcLen * 1.8D));
		for (int i = 0; i < nSrcLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case 58: // ':'
				if (bIsNewLine) {
					bIsQuote = true;
					retBuff.append("<font color=" + p_sQuoteColor + ">:");
				} else {
					retBuff.append(":");
				}
				bIsNewLine = false;
				break;

			case 32: // ' '
				retBuff.append(_bChangeBlank ? "&nbsp;" : " ");
				bIsNewLine = false;
				break;

			case 60: // '<'
				retBuff.append("&lt;");
				bIsNewLine = false;
				break;

			case 62: // '>'
				retBuff.append("&gt;");
				bIsNewLine = false;
				break;

			case 34: // '"'
				retBuff.append("&quot;");
				bIsNewLine = false;
				break;

			case 38: // '&'
				retBuff.append("&amp;");
				bIsNewLine = false;
				break;

			case 9: // '\t'
				retBuff.append(_bChangeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;" : "    ");
				bIsNewLine = false;
				break;

			case 10: // '\n'
				if (bIsQuote) {
					bIsQuote = false;
					retBuff.append("</font>");
				}
				retBuff.append("<br>");
				bIsNewLine = true;
				break;

			default:
				retBuff.append(cTemp);
				bIsNewLine = false;
				break;
			}
		}

		if (bIsQuote)
			retBuff.append("</font>");
		return retBuff.toString();
	}

	public static String transJsDisplay(String _sContent) {
		if (_sContent == null)
			return "";
		char srcBuff[] = _sContent.toCharArray();
		int nSrcLen = srcBuff.length;
		StringBuffer retBuff = new StringBuffer((int) ((double) nSrcLen * 1.5D));
		for (int i = 0; i < nSrcLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case 60: // '<'
				retBuff.append("&lt;");
				break;

			case 62: // '>'
				retBuff.append("&gt;");
				break;

			case 34: // '"'
				retBuff.append("&quot;");
				break;

			default:
				retBuff.append(cTemp);
				break;
			}
		}

		return retBuff.toString();
	}

	public static String transDisplayMark(String _strSrc, char p_chrMark) {
		if (_strSrc == null)
			return "";
		char buff[] = new char[_strSrc.length()];
		for (int i = 0; i < buff.length; i++)
			buff[i] = p_chrMark;

		return new String(buff);
	}

	public static String filterForSQL(String _sContent) {
		if (_sContent == null)
			return "";
		int nLen = _sContent.length();
		if (nLen == 0)
			return "";
		char srcBuff[] = _sContent.toCharArray();
		StringBuffer retBuff = new StringBuffer((int) ((double) nLen * 1.5D));
		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case 39: // '\''
				retBuff.append("''");
				break;

			case 59: // ';'
				boolean bSkip = false;
				for (int j = i + 1; j < nLen && !bSkip; j++) {
					char cTemp2 = srcBuff[j];
					if (cTemp2 != ' ') {
						if (cTemp2 == '&')
							retBuff.append(';');
						bSkip = true;
					}
				}

				if (!bSkip)
					retBuff.append(';');
				break;

			default:
				retBuff.append(cTemp);
				break;
			}
		}

		return retBuff.toString();
	}

	public static String filterForXML(String _sContent) {
		if (_sContent == null)
			return "";
		char srcBuff[] = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";
		StringBuffer retBuff = new StringBuffer((int) ((double) nLen * 1.8D));
		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case 38: // '&'
				retBuff.append("&amp;");
				break;

			case 60: // '<'
				retBuff.append("&lt;");
				break;

			case 62: // '>'
				retBuff.append("&gt;");
				break;

			case 34: // '"'
				retBuff.append("&quot;");
				break;

			case 39: // '\''
				retBuff.append("&apos;");
				break;

			default:
				retBuff.append(cTemp);
				break;
			}
		}

		return retBuff.toString();
	}

	public static String filterForHTMLValue(String _sContent) {
		if (_sContent == null)
			return "";
		char srcBuff[] = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";
		StringBuffer retBuff = new StringBuffer((int) ((double) nLen * 1.8D));
		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case 38: // '&'
				if (i + 1 < nLen) {
					cTemp = srcBuff[i + 1];
					if (cTemp == '#')
						retBuff.append("&");
					else
						retBuff.append("&amp;");
				} else {
					retBuff.append("&amp;");
				}
				break;

			case 60: // '<'
				retBuff.append("&lt;");
				break;

			case 62: // '>'
				retBuff.append("&gt;");
				break;

			case 34: // '"'
				retBuff.append("&quot;");
				break;

			default:
				retBuff.append(cTemp);
				break;
			}
		}

		return retBuff.toString();
	}

	public static String filterForUrl(String _sContent) {
		if (_sContent == null)
			return "";
		char srcBuff[] = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";
		StringBuffer retBuff = new StringBuffer((int) ((double) nLen * 1.8D));
		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case 37: // '%'
				retBuff.append("%25");
				break;

			case 63: // '?'
				retBuff.append("%3F");
				break;

			case 35: // '#'
				retBuff.append("%23");
				break;

			case 38: // '&'
				retBuff.append("%26");
				break;

			case 32: // ' '
				retBuff.append("%20");
				break;

			default:
				retBuff.append(cTemp);
				break;
			}
		}

		return retBuff.toString();
	}

	public static String filterForJs(String _sContent) {
		if (_sContent == null)
			return "";
		char srcBuff[] = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";
		StringBuffer retBuff = new StringBuffer((int) ((double) nLen * 1.8D));
		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case 34: // '"'
				retBuff.append("\\\"");
				break;

			case 92: // '\\'
				retBuff.append("\\\\");
				break;

			case 10: // '\n'
				retBuff.append("\\n");
				break;

			case 13: // '\r'
				retBuff.append("\\r");
				break;

			case 12: // '\f'
				retBuff.append("\\f");
				break;

			case 9: // '\t'
				retBuff.append("\\t");
				break;

			case 47: // '/'
				retBuff.append("\\/");
				break;

			default:
				retBuff.append(cTemp);
				break;
			}
		}

		return retBuff.toString();
	}

	public static String numberToStr(int _nValue) {
		return numberToStr(_nValue, 0);
	}

	public static String numberToStr(int _nValue, int _length) {
		return numberToStr(_nValue, _length, '0');
	}

	public static String numberToStr(int _nValue, int _length, char _chrFill) {
		String sValue = String.valueOf(_nValue);
		return expandStr(sValue, _length, _chrFill, true);
	}

	public static String numberToStr(long _lValue) {
		return numberToStr(_lValue, 0);
	}

	public static String numberToStr(long _lValue, int _length) {
		return numberToStr(_lValue, _length, '0');
	}

	public static String numberToStr(long _lValue, int _length, char _chrFill) {
		String sValue = String.valueOf(_lValue);
		return expandStr(sValue, _length, _chrFill, true);
	}

	public static String circleStr(String _strSrc) {
		if (_strSrc == null)
			return null;
		String sResult = "";
		int nLength = _strSrc.length();
		for (int i = nLength - 1; i >= 0; i--)
			sResult = sResult + _strSrc.charAt(i);

		return sResult;
	}

	public static final boolean isChineseChar(int c) {
		return c > 127;
	}

	public static final int getCharViewWidth(int c) {
		return isChineseChar(c) ? 2 : 1;
	}

	public static final int getStringViewWidth(String s) {
		if (s == null || s.length() == 0)
			return 0;
		int iWidth = 0;
		int iLength = s.length();
		for (int i = 0; i < iLength; i++)
			iWidth += getCharViewWidth(s.charAt(i));

		return iWidth;
	}

	public static String truncateStr(String _string, int _maxLength) {
		return truncateStr(_string, _maxLength, "..");
	}

	public static String truncateStr(String _string, int _maxLength, String _sExt) {
		if (_string == null)
			return null;
		if (_sExt == null)
			_sExt = "..";
		int nSrcLen = getStringViewWidth(_string);
		if (nSrcLen <= _maxLength)
			return _string;
		int nExtLen = getStringViewWidth(_sExt);
		if (nExtLen >= _maxLength)
			return _string;
		int iLength = _string.length();
		int iRemain = _maxLength - nExtLen;
		StringBuffer sb = new StringBuffer(_maxLength + 2);
		for (int i = 0; i < iLength; i++) {
			char aChar = _string.charAt(i);
			int iNeed = getCharViewWidth(aChar);
			if (iNeed > iRemain) {
				sb.append(_sExt);
				break;
			}
			sb.append(aChar);
			iRemain -= iNeed;
		}

		return sb.toString();
	}

	public static String filterForJDOM(String _string) {
		if (_string == null)
			return null;
		char srcBuff[] = _string.toCharArray();
		int nLen = srcBuff.length;
		StringBuffer dstBuff = new StringBuffer(nLen);
		for (int i = 0; i < nLen; i++) {
			char aChar = srcBuff[i];
			if (isValidCharOfXML(aChar))
				dstBuff.append(aChar);
		}

		return dstBuff.toString();
	}

	public static boolean isValidCharOfXML(char _char) {
		return _char == '\t' || _char == '\n' || _char == '\r' || ' ' <= _char && _char <= '\uD7FF'
				|| '\uE000' <= _char && _char <= '\uFFFD' || '\0' <= _char && _char <= '\0';
	}

	public static int getBytesLength(String _string) {
		if (_string == null)
			return 0;
		char srcBuff[] = _string.toCharArray();
		int nGet = 0;
		for (int i = 0; i < srcBuff.length; i++) {
			char aChar = srcBuff[i];
			nGet += aChar > '\177' ? 2 : 1;
		}

		return nGet;
	}

	public static String[] split(String _str, String _sDelim) {
		if (_str == null || _sDelim == null)
			return new String[0];
		StringTokenizer stTemp = new StringTokenizer(_str, _sDelim);
		int nSize = stTemp.countTokens();
		if (nSize == 0)
			return new String[0];
		String str[] = new String[nSize];
		for (int i = 0; stTemp.hasMoreElements(); i++)
			str[i] = stTemp.nextToken();

		return str;
	}
	
	public static HashSet<String> splitToSet(String _str, String _sDelim) {
		HashSet<String> set = new HashSet<String>();
		String str[] = split(_str, _sDelim);
		for(int i=0; i<str.length; i++){
			set.add(str[i]);
		}
		return set;
	}

	public static int countTokens(String _str, String _sDelim) {
		StringTokenizer stTemp = new StringTokenizer(_str, _sDelim);
		return stTemp.countTokens();
	}

	public static int[] splitToInt(String _str, String _sDelim) {
		StringTokenizer stTemp = new StringTokenizer(_str, _sDelim);
		int arInt[] = new int[stTemp.countTokens()];
		for (int nIndex = 0; stTemp.hasMoreElements(); nIndex++) {
			String sValue = (String) stTemp.nextElement();
			arInt[nIndex] = Integer.parseInt(sValue.trim());
		}

		return arInt;
	}

	public static final boolean isContainChineseChar(String _str) {
		if (_str == null)
			return false;
		return _str.getBytes().length != _str.length();
	}

	public static String join(ArrayList _arColl, String _sSeparator) {
		if (_arColl == null)
			return null;
		else
			return join(_arColl.toArray(), _sSeparator);
	}

	public static String join(Object _arColl[], String _sSeparator) {
		if (_arColl == null || _arColl.length == 0 || _sSeparator == null)
			return null;
		if (_arColl.length == 1)
			return _arColl[0].toString();
		StringBuffer result = new StringBuffer(_arColl[0].toString());
		for (int i = 1; i < _arColl.length; i++) {
			result.append(_sSeparator);
			result.append(_arColl[i].toString());
		}

		return result.toString();
	}

	public static String getArea(String src, String head, String tail, int start) {
		int intHead, intTail;

		if (head.equals(""))
			intHead = 0;
		else
			intHead = src.indexOf(head, start);

		if (tail.equals(""))
			intTail = src.length();
		else
			intTail = src.indexOf(tail, intHead + head.length());

		if (intHead < 0 || intTail < 0)
			return "";
		else
			return src.substring(intHead + head.length(), intTail);
	}

	public static String clearArea(String src, String head, String tail) {
		while (src.indexOf(head) != -1) {
			src = left(src, head) + right(src, tail);
		}

		return src;
	}

	public static String right(String str, String substr) {
		if (str == null || substr == null)
			return "";
		int start = str.indexOf(substr);
		return start < 0 ? "" : str.substring(start + substr.length());
	}

	public static String rightLast(String str, String substr) {
		if (str == null || substr == null)
			return "";
		int start = str.lastIndexOf(substr);
		return start < 0 ? "" : str.substring(start + substr.length());
	}

	public static String areaLast(String str, String strStart, String strEnd) {
		if (str == null || strStart == null || strEnd == null)
			return "";
		str = rightLast(str, strStart);
		str = left(str, strEnd);
		return str;
	}

	public static String left(String str, String substr) {
		if (str == null || substr == null)
			return "";
		int start = str.indexOf(substr);
		return start < 0 ? "" : str.substring(0, start);
	}

	public static String leftLast(String str, String substr) {
		if (str == null || substr == null)
			return "";
		int start = str.lastIndexOf(substr);
		return start < 0 ? "" : str.substring(0, start);
	}

	public static String area(String str, String strStart, String strEnd) {
		if (str == null || strStart == null || strEnd == null)
			return "";
		str = right(str, strStart);
		str = left(str, strEnd);
		return str;
	}

	public static String area(String str, int intStart, int intEnd) {
		if (str == null)
			return "";
		if (intStart >= intEnd)
			return "";
		if (str.length() < intStart)
			return "";
		return str.length() < intEnd ? str.substring(intStart, str.length()) : str.substring(intStart, intEnd);
	}

	public static String area(String str, int intStart) {
		if (str == null)
			return "";
		int intEnd = str.length();
		if (intStart >= intEnd)
			return "";
		if (str.length() < intStart)
			return "";
		return str.length() < intEnd ? str.substring(intStart, str.length()) : str.substring(intStart, intEnd);
	}

	/**
	 * 使用srcString字符串里面的变量，在dstModel查找相应的值，并替换。srcString的变量由placeholder分隔。
	 * 如：
	 * srcString="这是${name}的${object}。"
	 * dstModel的Map里面存在: name=张三, object=书
	 * placeholder="${}"
	 * 则替换后返回的字符串是  "这是张三的书。"
	 *
	 * @param srcString
	 * @param dstModel
	 * @param placeholder
	 * @return
	 */
	
	public static String replace(String srcString, Map dstModel, String placeholder)
	{
		StringBuffer sb = new StringBuffer();
		StringBuffer varSb = new StringBuffer();
		char c;
		
		final char firstChar, secondChar, thirdChar;
		firstChar = placeholder.charAt(0);
		secondChar = placeholder.charAt(1);
		thirdChar = placeholder.charAt(2);
		
		boolean isVar = false;
		for(int i=0; i<srcString.length(); i++)
		{
			c = srcString.charAt(i);
			if( c == firstChar)
			{
					i++;
					c = srcString.charAt(i);
					if(c==secondChar)
					{
						isVar = true;
						varSb = new StringBuffer();			
					}
					else
					{
						sb.append(firstChar);
						sb.append(c);
					}
			}
			else if( c == thirdChar )
			{
					if(isVar)
					{
						sb.append(dstModel.get(varSb.toString()));
						isVar = false;
					}
					else
					{
						sb.append(thirdChar);
					}
			}
			else
			{
				if(isVar)
				{
					varSb.append(c);				
				}
				else
				{
					sb.append(c);
				}		
			}
		}
	
		return sb.toString();
	}
}
