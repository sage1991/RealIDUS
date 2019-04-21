package com.idus.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormmatUtil {
	
	private static DateTimeFormatter todayFormat = DateTimeFormatter.ofPattern("HH:mm");
	private static DateTimeFormatter notTodayFormat = DateTimeFormatter.ofPattern("yy/MM/dd");
	
	
	public static String format(LocalDateTime localDateTime) {
		if(localDateTime == null) {
			return "";
		} else {
			if(isToday(localDateTime)) {
				return todayFormat.format(localDateTime);
			} else {
				return notTodayFormat.format(localDateTime);
			}
		}
	}
	
	private static boolean isToday(LocalDateTime localDateTime) {
		if(localDateTime == null) {
			return false;
		}
		LocalDate today = LocalDate.now();
		if(today.getMonth() == localDateTime.getMonth() && today.getYear() == localDateTime.getYear() && today.getDayOfMonth() == localDateTime.getDayOfMonth()) {
			return true;
		}
		return false;
	}
	
}
