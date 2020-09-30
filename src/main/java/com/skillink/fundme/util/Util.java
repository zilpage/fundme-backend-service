package com.skillink.fundme.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Util {



	public static boolean isEmptyString(String str) {
		str = str == null ? "" : str.trim();
		return str == null || str.equalsIgnoreCase("") || str.equalsIgnoreCase("null");
	}


	public static String getDateStringByFormat(String format, Date value) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		String date = dateFormat.format(value);

		return date;
	}

	public static Date getCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		sdfDate.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		sdfDate.format(0);
		Date now = new Date();
//	    String strDate = sdfDate.format(now);
		return now;
	}
}
