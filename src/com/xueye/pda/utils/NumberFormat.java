package com.xueye.pda.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFormat {

	public static String getDouble(Double d) {
		DecimalFormat df = new DecimalFormat("########0.00");
		return df.format(Math.abs(d));
	}

	public static boolean isMobileNO(String mobiles) {
//		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Pattern p = Pattern.compile("[1][3578]\\d{9}");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
