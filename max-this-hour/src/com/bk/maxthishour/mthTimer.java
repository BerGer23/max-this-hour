package com.bk.maxthishour;

import java.util.Timer;
import java.util.TimerTask;

public class mthTimer {
	static mthTimer myInstance;
	Timer timer;

	final int intervalwork = 60 * 48;
	final int intervalpause = 60 * 12;

	private int interval = 0;
	private int mode = 0; // 0 = nichts passiert, 1 run in work, 2 run in pause

	public mthTimer() {
		timer = new Timer();
	}

	public static mthTimer getInstance() {
		if (myInstance == null) {
			myInstance = new mthTimer();
		}
		return myInstance;
	}

	public void startTimer() {
		switch (mode) {
		case 0:
			interval = intervalwork;
			mode++;
			break;
		case 1:
			interval = intervalpause;
			mode++;
			break;
		}
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				iterateInterval();
			}
		}, 1000, 1000);
	}

	private void iterateInterval() {
		if (interval == 1) {
			this.timerRanOut();
		}
		interval--;
	}

	private void timerRanOut() {
		timer.cancel();
		switch (mode) {
		case 1:
			mode = 2;
			break;
		case 2:
			mode = 1;
			break;
		}
	}

	public int getMode() {
		return mode;
	}

}
