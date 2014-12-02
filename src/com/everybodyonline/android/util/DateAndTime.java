package com.everybodyonline.android.util;

import java.util.Date;

public class DateAndTime {

	public static Date getDateFromLong(long longtime) {

		Date returndate = new Date(longtime);
		return returndate;
	}
	
	public static Long getLongFromDate(Date date) {

		long returnlong = (long) date.getTime();
		return returnlong;
	}
}
