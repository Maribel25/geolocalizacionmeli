package com.meli.utils;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	
	public static final String ddMMyyy = "dd/MM/YYYY hh:mm:ss";
	public static final String hhMMss = "hh:mm:ss";

	/**
	 * formatDate
	 * @param date
	 * @param dateFormat
	 * @return String
	 */
	public static String formatDate(Date date, String dateFormat) {
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat); 
		return sdf.format(date);
	}
	
	/**
	 * ZonedDateTime
	 * @param timeZone
	 * @return String
	 */
	public static String getZonedTime(String timeZone) {
		ZoneId zone = ZoneId.of(timeZone);
		ZonedDateTime date = ZonedDateTime.now(zone);
		return date.format(DateTimeFormatter.ofPattern(hhMMss));
	}
	
}
