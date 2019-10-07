package com.globant.fligth.api.exercise.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static LocalDate getDateFromString(String dateString) {
		if (dateString == null)
			return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(dateString, formatter);
		return dateTime;
	}

	public static boolean isSameDay(LocalDate localDate, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR) == localDate.getYear()
				&& (cal.get(Calendar.MONTH) + 1) == localDate.getMonthValue()
				&& cal.get(Calendar.DAY_OF_YEAR) == localDate.getDayOfYear();
	}
}
