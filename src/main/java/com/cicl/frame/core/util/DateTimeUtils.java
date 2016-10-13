/** 
 * @(#)DateTimeUtils.java 1.0.0 2008-2-22 下午02:01:49  
 *  
 * Copyright 2008 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Class DateTimeUtils
 * TODO Purpose/description of class 
 *
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2008-2-22 下午02:01:49  
 */
public class DateTimeUtils implements Cloneable, Serializable {
	private Date date;
	private SimpleDateFormat formater;
	public static final int FORMAT_DEFAULT = 0;
	public static final int FORMAT_LONG = 1;
	public static final int FORMAT_SHORT = 2;
	public static final String DEF_DATE_FORMAT_PRG = "yyyy-MM-dd";
	public static final String DEF_TIME_FORMAT_PRG = "HH:mm:ss";
	public static final String DEF_DATETIME_FORMAT_PRG = "yyyy-MM-dd HH:mm:ss";
	public static final String DEF_DATETIME_FORMAT_DB = "YYYY-MM-DD HH24:MI:SS";
    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int DAY = 3;
    public static final int HOUR = 4;
    public static final int MINUTE = 5;
    public static final int SECOND = 6;
    public static final int QUATER = 11;
    public static final int WEEK = 12;
    public static final int DAY_OF_MONTH = 13;
    public static final int WEEK_OF_MONTH = 14;
    public static final int DAY_OF_YEAR = 15;
    public static final int WEEK_OF_YEAR = 16;
    public static final long ADAY_MILLIS = 86400000;

	public DateTimeUtils() {
		date = null;
		formater = null;
		setDateTimeWithCurrentTime();
	}

	public DateTimeUtils(long lDate) {
		date = null;
		formater = null;
		date = new Date(lDate);
	}

	public void setDateTimeWithCurrentTime() {
		if (date == null)
			date = new Date(System.currentTimeMillis());
		else
			date.setTime(System.currentTimeMillis());
	}

	public String toString() {
		return toString("yyyy-MM-dd HH:mm:ss");
	}

	public String toString(String sFormat) {
		if (date == null)
			return null;
		try {
			return getDateTimeAsString(sFormat);
		} catch (Exception ex) {
			return null;
		}
	}

	public String getDateTimeAsString(String sFormat) throws Exception {
		if (date == null)
			return null;
		try {
			setDateTimeFormat(sFormat);
			return formater.format(date);
		} catch (Exception ex) {
			throw new Exception(
					"invalid datetime format! (DateTimeUtils.getDateTimeAsString)",
					ex);
		}
	}

	public void setDateTimeFormat(String sFormat) {
		if (formater == null)
			formater = new SimpleDateFormat(sFormat);
		else
			formater.applyPattern(sFormat);
	}

	public long getTimeInMillis() {
		return date != null ? date.getTime() : 0L;
	}
	
    public boolean setDateTimeWithString(String p_sValue, String p_sFormat)
			throws Exception {
		try {
			SimpleDateFormat dtFormat = new SimpleDateFormat(p_sFormat);
			date = dtFormat.parse(p_sValue);
			return true;
		} catch (Exception ex) {
			throw new Exception("invalid datetime format! ", ex);
		}
	}
    
    public Date toDate() {
		if (date == null)
			return null;
		else
			return new java.sql.Date(date.getTime());
	}
    
    public long dateDiff(int p_nPart, Date p_dtAnother) throws Exception {
		if (p_dtAnother == null)
			return 0;
		if (this.date == null)
			return 0;
		if (p_nPart == YEAR)
			return dateDiff_year(p_dtAnother);
		if (p_nPart == MONTH)
			return dateDiff_month(p_dtAnother);
		long lMyTime = date != null ? date.getTime() : 0;
		long lAnotherTime = p_dtAnother != null ? p_dtAnother.getTime() : 0;
		long lDiffTime = (lMyTime - lAnotherTime) / 1000;
		switch (p_nPart) {
		case DAY: // '\003'
			return lDiffTime / 86400;

		case HOUR: // '\004'
			return lDiffTime / 3600;

		case MINUTE: // '\005'
			return lDiffTime / 60;

		case SECOND: // '\006'
			return lDiffTime;

		case QUATER: // '\013'
			return lDiffTime / 86400 / 91;

		case WEEK: // '\f'
			return lDiffTime / 86400 / 7;

		default:
			throw new Exception(
					"invalid arguments(DateTimeUtils.dateDiff(int,java.util.Date))");
		}
	}

	private long dateDiff_year(Date p_dtAnother) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getDefault());
		cal.setTime(date);
		int nYear1 = cal.get(1);
		int nMonth1 = cal.get(2);
		cal.setTime(p_dtAnother);
		int nYear2 = cal.get(1);
		int nMonth2 = cal.get(2);
		if (nYear1 == nYear2)
			return 0;
		if (nYear1 > nYear2)
			return (long) ((nYear1 - nYear2) + (nMonth1 < nMonth2 ? -1 : 0));
		else
			return (long) ((nYear1 - nYear2) + (nMonth1 <= nMonth2 ? 0 : 1));
	}

	public long dateDiff_month(Date p_dtAnother) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getDefault());
		cal.setTime(date);
		int nMonths1 = cal.get(1) * 12 + cal.get(2);
		int nDay1 = cal.get(5);
		cal.setTime(p_dtAnother);
		int nMonths2 = cal.get(1) * 12 + cal.get(2);
		int nDay2 = cal.get(5);
		if (nMonths1 == nMonths2)
			return 0L;
		if (nMonths1 > nMonths2)
			return (long) ((nMonths1 - nMonths2) + (nDay1 >= nDay2 ? 0 : -1));
		else
			return (long) ((nMonths1 - nMonths2) + (nDay1 <= nDay2 ? 0 : 1));
	}

}
