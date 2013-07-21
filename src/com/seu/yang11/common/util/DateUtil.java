/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: DateUtil.java, v 0.1 2012-4-25 ÏÂÎç11:13:31 narutoying Exp $
 */
public class DateUtil {

	public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";;

	public static Date convertToDate(String dateStr, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			if (StringUtil.isNotBlank(dateStr)) {
				return dateFormat.parse(dateStr);
			} else {
				return null;
			}
		} catch (ParseException e) {
			return null;
		}
	}

	public static String convertToString(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(date);
		} else {
			return null;
		}
	}
}
