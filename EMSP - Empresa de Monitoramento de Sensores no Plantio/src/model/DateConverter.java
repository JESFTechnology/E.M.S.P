package model;

import java.util.Date;

public class DateConverter {
	@SuppressWarnings("deprecation")
	public static String getConvertDate(Date date) {
		return date.getDay()+"/"+date.getMonth()+"/"+date.getYear();
	}
	
	@SuppressWarnings("deprecation")
	public static String getConvertTime(Date date) {
		return date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	}
	
	public static String getConvertDatetime(Date date) {
		return getConvertTime(date)+" "+getConvertDate(date);
	}
}
