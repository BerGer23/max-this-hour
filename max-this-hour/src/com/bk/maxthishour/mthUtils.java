package com.bk.maxthishour;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;

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
		Gdx.app.log(MaxThisHour.strTag, "trying to mute");
		if (MaxThisHour.getMySpecialFeatureObject() != null)
			MaxThisHour.getMySpecialFeatureObject().Mute();
	}

	public static void Unmute() {
		Gdx.app.log(MaxThisHour.strTag, "trying to unmute");
		if (MaxThisHour.getMySpecialFeatureObject() != null)
			MaxThisHour.getMySpecialFeatureObject().Unmute();
	}

	public static void notifyUser() {
		switch (Gdx.app.getType()) {

		case Android:
			// android specific code
			Gdx.input.vibrate(mthGUI.getInstance().updateMsToVibrate());
			break;
		case Desktop:
			// desktop specific code
			break;
		case WebGL:
			// / HTML5 specific code
			break;
		default:
			break;
		}
	}
}
