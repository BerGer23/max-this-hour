package com.bk.maxthishour;

import android.media.AudioManager;
import android.util.Log;

public class SpecialFeaturesAndroid implements SpecialFeatures {

	AudioManager volumeControl;

	public SpecialFeaturesAndroid(AudioManager myAM) {
		volumeControl = myAM;
	}

	@Override
	public void Mute() {
		Log.d("MAHinfo", "clicked mute");
		volumeControl.setStreamMute(AudioManager.STREAM_RING, true);
	}

	@Override
	public void Unmute() {
		Log.d("MAHinfo", "clicked unmute");
		volumeControl.setStreamMute(AudioManager.STREAM_RING, false);
	}

	@Override
	public void dispose() {
	}

}
