package com.target.myretail.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
/**
 * MyRetailUtil is a utility class for converting the date format.
 * @author Mourya Mandava
 */
public class MyRetailUtil {
	public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	    return java.util.Date
	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	      .toInstant());
	}
}
