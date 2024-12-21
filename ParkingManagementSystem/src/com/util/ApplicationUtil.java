package com.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ApplicationUtil {
	public List<String> splitRecord(String[] details) {
		return Arrays.asList(details);
	}

	public LocalDateTime convertUtilDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		return dateTime;
	}

	// Converts java.util.Date to java.sql.Date
	public java.sql.Timestamp convertSqlDate(LocalDateTime obj) {
		Timestamp sqlDateTime = Timestamp.valueOf(obj);
		return sqlDateTime;
	}

	public boolean validLicense(String license) {
		return Pattern.matches("^TN\\d{6}$", license);
	}
	
	public boolean validSlotDetails(String slot) {
		return Pattern.matches("^Zone-[A-Da-d]{1}$", slot);
	}
	

}
