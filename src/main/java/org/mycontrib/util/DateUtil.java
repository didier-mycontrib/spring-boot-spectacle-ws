package org.mycontrib.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static DateFormat ddf = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat tdf = new SimpleDateFormat("HH:mm:ss");
	
	public static Date javaDateFromStringDate(String dateAsString) {
		Date d =null;
		try {
			d= ddf.parse(dateAsString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public static Date javaDateFromStringTime(String timeAsString) {
		Date d =null;
		try {
			d= tdf.parse(timeAsString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
}
