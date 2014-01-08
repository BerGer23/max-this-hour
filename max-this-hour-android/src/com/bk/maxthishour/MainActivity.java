package com.bk.maxthishour;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {

	AudioManager myAM;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;


		myAM = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		initialize(new MaxThisHour(new SpecialFeaturesAndroid(myAM)), cfg);
	}

}
