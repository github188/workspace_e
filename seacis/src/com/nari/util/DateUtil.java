package com.nari.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.MissingResourceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nari.exception.ExceptionHandle;

public class DateUtil {

	private static Log log = LogFactory.getLog(DateUtil.class);
	private static String pattern = "yyyy-MM-dd";
	private static SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	private static String defaultDatePattern = null;
	private static String timePattern = "HH:mm";
	public static final String TYPE_DATE = "D"; 				// 日期
	public static final String TYPE_TIME = "T"; 				// 时间
	public static final String TYPE_DATETIME = "DT"; 			// 日期时间
	public static final String STYLE_XML = "X"; 				// XML日期时间格式
	public static final String STYLE_AD = "AD"; 				// 日期时间格式：CCYYMMDDhhmmss
	public static final String STYLE_ROC = "R"; 				// 日期时间格式：YYYMMDDhhmmss
	public static final String STYLE_FORMAT = "F"; 				// 日期时间格式：CCYY-MM-DD
	public static final String STYLE_FORMAT_FOR_USER = "FU"; 	// 日期时间格式:CCYY/MM/DD
	public static final SimpleDateFormat DATE_FORMAT_FULL = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_MEDIUM = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_MEDIUM_BBS = new SimpleDateFormat(
			"MM-dd HH:mm");
	public static final SimpleDateFormat DATE_FORMAT_SHORT_BBS = new SimpleDateFormat(
			"MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_SHORT_BBSFEN = new SimpleDateFormat(
			"HH:mm");
	public static final SimpleDateFormat DATE_FORMAT_FULL_ZH = new SimpleDateFormat(
			"yyyy年MM月dd日 HH时mm分ss秒");
	public static final SimpleDateFormat DATE_FORMAT_MEDIUM_ZH = new SimpleDateFormat(
			"yyyy年MM月dd日 HH时mm分");
	public static final SimpleDateFormat DATE_FORMAT_SHORT_ZH = new SimpleDateFormat(
			"yyyy年MM月dd日");

	public static String getDatePattern() {
		try {
			defaultDatePattern = "yyyy-MM-dd";
		} catch (MissingResourceException mse) {
			defaultDatePattern = "MM/dd/yyyy";
		}
		return defaultDatePattern;
	}

	public static String getDateTimePattern() {
		return DateUtil.getDatePattern() + " HH:mm:ss.S";
	}

	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return (date);
	}

	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	public static Date convertStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDatePattern());
			}

			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException e) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			ExceptionHandle.handleUnCheckedException(e);
			throw new ParseException(e.getMessage(), e.getErrorOffset());

		}

		return aDate;
	}

	public synchronized static String getDateTime(Calendar calendar,
			String type, String style) {
		String myDateTime = "";
		if (type == null || type.equals("")) {
			type = TYPE_DATETIME;
		}
		if (style == null || style.equals("")) {
			style = STYLE_AD;
		}
		String year, month, day, hour, min, sec;
		if (style.equals(STYLE_ROC)) {
			year = padding(calendar.get(Calendar.YEAR) - 1911, 2);
		} else {
			year = padding(calendar.get(Calendar.YEAR), 4);
		}
		month = padding(calendar.get(Calendar.MONTH) + 1, 2);
		day = padding(calendar.get(Calendar.DATE), 2);
		hour = padding(calendar.get(Calendar.HOUR_OF_DAY), 2);
		min = padding(calendar.get(Calendar.MINUTE), 2);
		sec = padding(calendar.get(Calendar.SECOND), 2);

		if (type.equals(TYPE_DATE) || type.equals(TYPE_DATETIME)) {
			myDateTime = year + month + day;
		}
		if (type.equals(TYPE_TIME) || type.equals(TYPE_DATETIME)) {
			myDateTime = myDateTime + hour + min + sec;
		}
		if (style.equals(STYLE_FORMAT)) {
			myDateTime = formateDateTime(myDateTime);
		} else if (style.equals(STYLE_XML)) {
			myDateTime = strTime2XMLTime(myDateTime);
		} else if (style.equals(STYLE_FORMAT_FOR_USER)) {
			myDateTime = formateDateTimeForUser(myDateTime);
		}

		return myDateTime;
	}

	/**
	 * 根据所需长度补空格
	 * @param 	srcString源字符串
	 * @param 	len所需长度
	 * @return	所需长度的字符串
	 */
	public synchronized static String padding(String srcString, int len) {
		String desString = null;
		srcString = cropping(srcString, len);
		int srcLen = srcString.getBytes().length;
		desString = srcString;
		for (int i = 0; i < (len - srcLen); i++) {
			desString = desString + " ";
		}
		return desString;
	}

	/**
	 * 根据所需长度补0
	 * @param srcLong源数
	 * @param len所需长度
	 * @return所长度的字符串
	 */
	public synchronized static String padding(long srcLong, int len) {
		String desString = null;
		String srcString = String.valueOf(srcLong);
		srcString = cropping(srcString, len);
		int srcLen = srcString.length();
		desString = srcString;
		for (int i = 0; i < (len - srcLen); i++) {
			desString = "0" + desString;
		}
		return desString;
	}

	/**
	 * 剪裁字符串
	 * @param srcString
	 * @param maxLen
	 * @return
	 */
	public synchronized static String cropping(String srcString, int maxLen) {
		String desString = null;
		byte[] desBytes = srcString.getBytes();
		if (desBytes.length > maxLen) {
			byte[] tmpBytes = cropping(desBytes, maxLen);
			desBytes = tmpBytes;
		}
		desString = new String(desBytes);
		return desString;
	}

	/**
	 * 剪裁字符串成字节
	 * @param srcBytes
	 * @param maxLen
	 * @return
	 */
	public synchronized static byte[] cropping(byte[] srcBytes, int maxLen) {
		byte[] desBytes = srcBytes;
		if (srcBytes.length > maxLen) {
			for (int i = 0; i < maxLen; i++) {
				if (srcBytes[i] < 0) {
					i++;
				}
				if (i == maxLen) {
					maxLen = maxLen - 1;
				}
			}
			byte[] tmpBytes = new byte[maxLen];
			System.arraycopy(srcBytes, 0, tmpBytes, 0, maxLen);
			desBytes = tmpBytes;
		}
		return desBytes;
	}

	/**
	 * 格式化日期时间
	 * @param myDateTime
	 * @return
	 */
	public synchronized static String formateDateTime(String myDateTime) {
		String rtnDateTime = "";
		if (myDateTime.length() == 8 || myDateTime.length() == 14) {
			rtnDateTime = myDateTime.substring(0, 4) + "-"
					+ myDateTime.substring(4, 6) + "-"
					+ myDateTime.substring(6, 8);
			if (myDateTime.length() == 14) {
				rtnDateTime = rtnDateTime + " ";
				myDateTime = myDateTime.substring(8);
			}
		}
		if (myDateTime.length() == 6) {
			rtnDateTime = rtnDateTime + myDateTime.substring(0, 2) + ":"
					+ myDateTime.substring(2, 4) + ":"
					+ myDateTime.substring(4, 6);
		}
		return rtnDateTime;
	}

	/**
	 * 把字符串时间转换成xml时间
	 * @param xmlTime
	 * @return
	 */
	public synchronized static String strTime2XMLTime(String xmlTime) {
		String rtnDateTime = "";
		String timezone = "+08:00";
		if (xmlTime == null
				|| (xmlTime.length() != 14 && xmlTime.length() != 6 && xmlTime
						.length() != 8)) {
			return rtnDateTime;
		}
		if (xmlTime.length() == 6 || xmlTime.length() == 8) {
			rtnDateTime = formateDateTime(xmlTime);
		} else if (xmlTime.length() == 14) {
			rtnDateTime = formateDateTime(xmlTime.substring(0, 8)) + "T"
					+ formateDateTime(xmlTime.substring(8, 14)) + timezone;
		}
		return rtnDateTime;
	}

	public synchronized static String formateDateTimeForUser(String myDateTime) {
		String rtnDateTime = "";
		if (myDateTime != null)
			myDateTime = myDateTime.trim();
		if (myDateTime.length() == 8 || myDateTime.length() == 14) {
			rtnDateTime = myDateTime.substring(0, 4) + "/"
					+ myDateTime.substring(4, 6) + "/"
					+ myDateTime.substring(6, 8);
			if (myDateTime.length() == 14) {
				rtnDateTime = rtnDateTime + " ";
				myDateTime = myDateTime.substring(8);
			}
		}
		if (myDateTime.length() == 7) {
			rtnDateTime = myDateTime.substring(0, 3) + "/"
					+ myDateTime.substring(3, 5) + "/"
					+ myDateTime.substring(5, 7);
		}
		if (myDateTime.length() == 6) {
			rtnDateTime = rtnDateTime + myDateTime.substring(0, 2) + ":"
					+ myDateTime.substring(2, 4) + ":"
					+ myDateTime.substring(4, 6);
		}
		return rtnDateTime;
	}

	/**
	 * 得到当前时间
	 * @param type
	 * @param style
	 * @return
	 */
	public synchronized static String getCurrentTime(String type, String style) {
		Calendar calendar = Calendar.getInstance();
		return getDateTime(calendar, type, style);
	}

	/**
	 * 日期加减操作
	 * @param source源日期
	 * @param field项
	 * （日，月，年)
	 * @param num数量
	 * + 为加，-为减
	 * @return
	 */
	public static Date dateRoler(Date source, int field, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(source);
		c.add(field, num);
		return c.getTime();
	}

	/**
	 * 将所给的Date对象转换为字符串
	 * @param source
	 *  要转换的Date对象
	 * @return 目标字符串
	 */
	public static String dateToString(Date source) {
		String result = "";
		result = sdf.format(source);
		return result;
	}

	public static Date addDate(Date date, int day) {
		Date result = null;
		if (date == null)
			return null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day1 = cal.get(Calendar.DATE);
			GregorianCalendar gregorianCalendar = new GregorianCalendar(year,
					month, day1);
			gregorianCalendar.add(Calendar.DATE, day);
			result = new java.sql.Date(gregorianCalendar.getTime().getTime());
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	/**
	 * 返回起止日期的小时数组(24点)
	 * @param startday
	 * @param endday
	 * @return
	 * @throws ParseException
	 */
	public static String[] getTime(String startday, String endday)
			throws ParseException {
		String[] returnArray = null;
		String[] hours = new String[24];
		for (int i = 0; i < hours.length; i++) {
			if (i < 10) {
				hours[i] = "0" + String.valueOf(i);
			} else {
				hours[i] = String.valueOf(i);
			}
		}
		int days = getDate(startday, endday) + 1;
		returnArray = new String[days * 24];
		int k = 0;
		String day = startday;
		for (int i = 0; i < days; i++) {
			for (int j = 0; j < hours.length; j++) {
				returnArray[k] = day + " " + hours[j];
				k++;
			}
			day = getNextDay(day);
		}

		return returnArray;

	}

	/**
	 * 返回起止日期的小时数组(96点)
	 * @param startday
	 * @param endday
	 * @return
	 * @throws ParseException
	 */
	public static String[] getTime96(String startday, String endday)
			throws ParseException {
		String[] returnArray = null;
		String[] hours = new String[24];
		String[] minute = { "00", "15", "30", "45" };

		for (int i = 0; i < hours.length; i++) {
			if (i < 10) {
				hours[i] = "0" + String.valueOf(i);
			} else {
				hours[i] = String.valueOf(i);
			}
		}
		int days = getDate(startday, endday) + 1;
		returnArray = new String[days * 96];
		int k = 0;
		String day = startday;
		for (int i = 0; i < days; i++) {
			for (int j = 0; j < hours.length; j++) {
				for (int m = 0; m < minute.length; m++) {
					returnArray[k] = day + " " + hours[j] + ":" + minute[m];
					k++;
				}
			}
			day = getNextDay(day);
		}

		return returnArray;

	}

	/**
	 * 返回起止日期的日期数组
	 * @param startday
	 * @param endday
	 * @return
	 * @throws ParseException
	 */
	public static String[] getDateArray(String startday, String endday)
			throws ParseException {
		String[] returnArray = null;
		int days = getDate(startday, endday) + 1;
		returnArray = new String[days];

		String day = startday;
		for (int i = 0; i < days; i++) {
			returnArray[i] = day;
			day = getNextDay(day);
		}

		return returnArray;

	}

	/**
	 * 取得起止日期之间的间隔天数
	 * @param startday
	 * @param endday
	 * @return
	 * @throws Exception
	 */
	public static int getDate(String startday, String endday) {
		long ei = 0;
		try {
			// 开始时间和结束时间(Date型)
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date start = new Date(sf.parse(startday).getTime());
			Date end = new Date(sf.parse(endday).getTime());

			Calendar startcal = Calendar.getInstance();
			Calendar endcal = Calendar.getInstance();
			startcal.setTime(start);
			endcal.setTime(end);

			// 分别得到两个时间的毫秒数
			long sl = startcal.getTimeInMillis();
			long el = endcal.getTimeInMillis();

			ei = el - sl;
		} catch (ParseException e) {
			ExceptionHandle.handleUnCheckedException(e);
		}
		// 根据毫秒数计算间隔天数
		return (int) (ei / (1000 * 60 * 60 * 24));

	}

	/**
	 * 获取当日日期
	 * @return
	 */
	public static String getToday() {
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取昨日日期
	 * @return
	 */
	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取明日日期
	 * @return
	 */
	public static String getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取前一周日期
	 * @return
	 */
	public static String getLastWeek() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 7);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取后一周日期
	 * @return
	 */
	public static String getNextWeek() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 7);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取前一月日期
	 * @return
	 */
	public static String getLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取后一月日期
	 * @return
	 */
	public static String getNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获得某日后一日日期
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static String getNextDay(String day) throws ParseException {
		String nextday = "";
		java.util.Date date = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, +1);
		nextday = sdf.format(cal.getTime());
		return nextday;
	}

	/**
	 * 获得某日前一日日期
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static String getBeforeDay(String day) throws ParseException {
		String nextday = "";
		java.util.Date date = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		nextday = sdf.format(cal.getTime());
		return nextday;
	}

	// 將傳入之字串日期格式轉為calendar,若不合法則throw APException
	public synchronized static Calendar string2Calendar(String dateString) {
		int year = 0, month = 0, date = 0, hour = 0, min = 0, sec = 0, myLen = 0;
		if (dateString == null || dateString.equals("")) {
			log.debug("string2Calendar():传入时间为空!");
			return null;
		}
		myLen = dateString.length();
		if (myLen == 8 || myLen == 14) {
			year = Integer.parseInt(dateString.substring(0, 4));
			month = Integer.parseInt(dateString.substring(4, 6)) - 1;
			date = Integer.parseInt(dateString.substring(6, 8));
			if (myLen == 14) {
				dateString = dateString.substring(8);
			}
		}

		if (dateString.length() == 6) {
			hour = Integer.parseInt(dateString.substring(0, 2));
			min = Integer.parseInt(dateString.substring(2, 4));
			sec = Integer.parseInt(dateString.substring(4, 6));
		} else if (dateString.length() == 4) {
			hour = Integer.parseInt(dateString.substring(0, 2));
			min = Integer.parseInt(dateString.substring(2, 4));
		}

		Calendar calendarObj = Calendar.getInstance();
		if (myLen == 8) {
			calendarObj.set(year, month, date);
			if (year != calendarObj.get(Calendar.YEAR)
					|| month != (calendarObj.get(Calendar.MONTH))
					|| date != calendarObj.get(Calendar.DATE)) {
				log.debug("日期格式错误!");
				return null;
			}
		} else if (myLen == 4) {
			calendarObj.set(Calendar.HOUR_OF_DAY, hour);
			calendarObj.set(Calendar.MINUTE, min);
			if (hour < 0 || hour >= 24 || min < 0 || min >= 60 || sec < 0
					|| sec >= 60) {
				log.debug("时间格式错误!");
				return null;
			}
		} else if (myLen == 6) {
			calendarObj.set(Calendar.HOUR_OF_DAY, hour);
			calendarObj.set(Calendar.MINUTE, min);
			calendarObj.set(Calendar.SECOND, sec);
			if (hour < 0 || hour >= 24 || min < 0 || min >= 60 || sec < 0
					|| sec >= 60) {
				log.debug("时间格式错误!");
				return null;
			}
		} else if (myLen == 14) {
			calendarObj.set(year, month, date, hour, min, sec);
			if (year != calendarObj.get(Calendar.YEAR)
					|| month != (calendarObj.get(Calendar.MONTH))
					|| date != calendarObj.get(Calendar.DATE)
					|| hour != calendarObj.get(Calendar.HOUR_OF_DAY)
					|| min != calendarObj.get(Calendar.MINUTE)
					|| sec != calendarObj.get(Calendar.SECOND)) {
				log.debug("日期或时间格式错误!");
				return null;
			}
		} else {
			log.debug("传入长度错误!");
			return null;
		}
		return calendarObj;
	}

	/**
	 * 取得当前时间 yyyy-MM-dd hh24:mi:ss
	 * @return
	 */
	public static String getFulltime() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT_FULL.format(cal.getTime());
	}

	public static void main(String[] args) {
		Calendar c1 = DateUtil.string2Calendar("1205");
		Calendar c2 = DateUtil.string2Calendar("1205");
		Calendar c3 = DateUtil.string2Calendar("1205");
		System.out.println(c1.equals(c2) && c1.equals(c3));
	}

	/**
	 * 得到当前日期
	 * @return String
	 */
	public static String getCurrentlyDate() {
		StringBuffer date = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		date.append(calendar.get(Calendar.YEAR));
		date.append("-");
		if (calendar.get(Calendar.MONTH) + 1 < 10) {
			date.append("0" + (calendar.get(Calendar.MONTH) + 1));
		} else {
			date.append("" + (calendar.get(Calendar.MONTH) + 1));
		}
		date.append("-");
		if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
			date.append("0" + calendar.get(Calendar.DAY_OF_MONTH));
		} else {
			date.append("" + calendar.get(Calendar.DAY_OF_MONTH));
		}
		return date.toString();
	}

	/**
	 * 得到一个日期所在月的第一天和最后一天
	 * @param sj时间
	 * 格式YYYY-MM-DD
	 * @return
	 */
	public static String[] getMonthFirsAndLast(String sj) {
		String[] dayResult = { "", "" };
		String strtmp = sj;
		if (sj.length() >= 7) {
			strtmp = strtmp.substring(0, 7);
		}
		Calendar lastCal = Calendar.getInstance();
		// 得到给日期所在月的第一天
		dayResult[0] = strtmp + "-01";
		// 得到给日期所在的月最后一天
		java.sql.Date curDate = java.sql.Date.valueOf(strtmp.substring(0, 5)
				+ String.valueOf(Integer.parseInt(strtmp.substring(5, 7)) + 1)
				+ "-01");
		lastCal.setTime(curDate);
		lastCal.add(Calendar.DATE, -1);
		String dayStr = lastCal.get(Calendar.DATE) + "";
		if (dayStr.length() == 1) {
			dayStr = "0" + dayStr;
		}
		dayResult[1] = strtmp + "-" + dayStr;

		return dayResult;
	}

	/**
	 * 得到当前月份，格式为：YYYYMM
	 * @return String
	 */
	public static String getCurrentMonth() {
		StringBuffer date = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		date.append(calendar.get(Calendar.YEAR));
		if (calendar.get(Calendar.MONTH) + 1 < 10) {
			date.append("0" + (calendar.get(Calendar.MONTH) + 1));
		} else {
			date.append("" + (calendar.get(Calendar.MONTH) + 1));
		}
		return date.toString();
	}

	/**
	 * 将所给的Date对象转换为字符串
	 * @param source要转换的Date对象
	 * @return 目标字符串
	 */
	public static String DateToString(Date source) {
		String result = "";
		result = sdf.format(source);
		return result;
	}

	/**
	 * 由java.util.Date到java.sql.Date的类型转换
	 * @param date
	 * @return Date
	 */
	public static Date getSqlDate(java.util.Date date) {
		return new Date(date.getTime());
	}

	/**
	 * 得到当前日期
	 * @return
	 */
	public static Date nowDate() {
		Calendar calendar = Calendar.getInstance();
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 得到某年某月第一天的日期
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 获得某一日期的前2天的日期
	 * @param date
	 * @return Date
	 */
	public static Date getPreviousDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day - 2);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 得到某年某月最后一天的日期
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		return getPreviousDate(getSqlDate(calendar.getTime()));
	}

	/**
	 * 由年月日构建java.sql.Date类型
	 * @param year
	 * @param month
	 * @param date
	 * @return Date
	 */
	public static Date buildDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 获取某年某月的天数
	 * @param year
	 * @param month
	 * @return int
	 */
	public static int getDayCountOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 0);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得某年某季度最后一天的日期
	 * @param year
	 * @param quarter
	 * @return Date
	 */
	public static Date getLastDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = quarter * 3;
		}
		return getLastDayOfMonth(year, month);

	}

	/**
	 * 获得某年某季度的第一天的日期
	 * @param year
	 * @param quarter
	 * @return Date
	 */
	public static Date getFirstDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = (quarter - 1) * 3 + 1;
		}
		return getFirstDayOfMonth(year, month);
	}

	/**
	 * 获得某年第一天的日期
	 * @param year
	 * @return Date
	 */
	public static Date getFirstDayOfYear(int year) {
		return getFirstDayOfMonth(year, 1);
	}

	/**
	 * 获得某年最后一天的日期
	 * @param year
	 * @return Date
	 */
	public static Date getLastDayOfYear(int year) {
		return getLastDayOfMonth(year, 12);
	}

	/**
	 * String到java.util.Date类型的转换
	 * @param param
	 * @return Date
	 * @throws java.text.ParseException
	 */
	public static java.util.Date StringToDate(String param)
			throws java.text.ParseException {
		if (param == null) {
			return null;
		} else {
			java.util.Date date = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				date = sdf.parse(param);
				return new Date(date.getTime());
			} catch (ParseException ex) {
				return null;
			}
		}
	}

	/**
	 * 将日期型转换成特定格式的日期字符串
	 * @param date
	 * @param partten
	 * @return
	 */
	public static String getDateString(java.util.Date date, String partten) {
		String result = null;
		String timezoneID = "GMT+8:00";
		SimpleDateFormat formatter = new SimpleDateFormat(partten,
				new java.util.Locale("zh", "CN"));
		java.util.TimeZone timezone = java.util.TimeZone
				.getTimeZone((String) timezoneID);
		Calendar calendar = new java.util.GregorianCalendar(timezone);
		formatter.setCalendar(calendar);
		result = formatter.format(date);
		return result;
	}

	/**
	 * 计算两个日期间相差的月份，包括两个日期
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static List<String> getBetweenDate(Date staDate, Date endDate) {
		List<String> ls = new ArrayList<String>();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(staDate);
		c2.setTime(endDate);
		for (; c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)
				|| c1.get(Calendar.MONTH) + 1 <= c2.get(Calendar.MONTH) + 1; c1
				.add(Calendar.MONTH, 1)) {
			ls.add(DateUtil.getDateString(c1.getTime(), "yyyy-MM"));
		}
		return ls;
	}

	/**
	 * 将字符串转换成java.util.Date
	 * @param
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date convertStrToDate(String s)
			throws java.text.ParseException {

		return convertStrToDate(s, "yyyy-MM-dd");
	}

	/**
	 * 将字符串转换成特定格式的java.util.Date
	 * @param
	 * @param pattern
	 * @return
	 * @throws java.text.ParseException
	 */
	public static java.util.Date convertStrToDate(String s, String pattern)
			throws java.text.ParseException {
		if ("".equals(s) || null == s) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(s);
		} catch (ParseException pe) {
			pe.printStackTrace();
			return null;
		}
	}

	/**
	 * 将日期转换成yyyy-MM-dd的日期字符串
	 * @param
	 * @return
	 */
	public static String getDateString(Date d) {
		return getDateString(d, "yyyy-MM-dd");
	}

	/**
	 * 根据不同格式的日期字符串返回下一个月的同样格式的日期字符串
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String getNextDateString(String date, String pattern)
			throws ParseException {
		Date d = DateUtil.convertStrToDate(date, pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, 1);
		return DateUtil.getDateString(c.getTime(), "yyyy-MM");
	}

	/**
	 * 计算一个日期加上或减去一个天数之后的日期
	 * @param date
	 * @param pattern
	 */
	public static Date addTime(Date noncedate, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(noncedate);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
}