package com.bk.maxthishour;

import java.text.DecimalFormat;

public class mthUtils {
	public static String intSecondsToStringMinSec(int input) {
		DecimalFormat df = new DecimalFormat("#00");
		int m, s;
		m = input / 60;
		s = input % 60;
		String tmp = new String();
		tmp = df.format(m) + ":" + df.format(s);
		return tmp;
	}

	public static void Mute() {
		MaxThisHour.getMySpecialFeatureObject().Mute();
	}

	public static void Unmute() {
		MaxThisHour.getMySpecialFeatureObject().Unmute();
	}
}
