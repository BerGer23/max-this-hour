package com.bk.maxthishour;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;

public class mthTimer {
	private static mthTimer myInstance;
	private boolean running = false;
	Timer timer;

	final int intervalwork = 60 * 48;
	final int intervalpause = 60 * 12;

	private int interval = 0;
	private int mode = 0; // 0 = nichts passiert, 1 run in work, 2 run in pause

	public mthTimer() {
	}

	public static mthTimer getInstance() {
		if (myInstance == null) {
			myInstance = new mthTimer();
		}
		return myInstance;
	}

	public void startstopTimer() {

		if (!running) {
			timer = new Timer();
			if (interval < 1) {
				switch (mode) {
				case 0:
					mode++;
				case 1:
					interval = intervalwork;
					break;
				case 2:
					interval = intervalpause;
					break;
				}
			}

			if (mthGUI.getInstance().isChkMuteChecked() && mode < 2)
				mthUtils.Mute();

			timer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					iterateInterval();
				}
			}, 1000, 1000);
			running = true;
		} else {
			mthUtils.Unmute();
			running = false;
			timer.cancel();
		}
	}

	private void iterateInterval() {
		if (interval == 1) {
			this.timerRanOut();
		}
		interval--;
	}

	private void timerRanOut() {
		Gdx.input.vibrate(500);
		timer.cancel();
		running = false;
		switch (mode) {
		case 1:
			mode = 2;
			break;
		case 2:
			mode = 1;
			break;
		}
		startstopTimer();
	}

	public int getMode() {
		return mode;
	}

	public int getInterval() {
		return interval;
	}

	public boolean getRunning() {
		return running;
	}

}
