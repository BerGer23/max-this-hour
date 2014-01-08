package com.bk.maxthishour;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;

/* Timer implements the 
 * 
 * 
 */
public class mthTimer {
	private static mthTimer myInstance;
	private boolean running = false;
	private Timer timer;

	final int intervalwork = 60 * 48;
	final int intervalpause = 60 * 12;

	private int interval = 0;
	private int mode = 0; // 0 = nichts passiert, 1 run in work, 2 run in pause

	private mthTimer() {
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
			Gdx.app.log(MaxThisHour.strTag,
					"interval: " + String.valueOf(interval));
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

			timer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					iterateInterval();
				}
			}, 1000, 1000);
			running = true;
		} else {
			running = false;
			timer.cancel();
		}
	}

	private void iterateInterval() {
		if (interval == 0) {
			this.timerRanOut();
			return;
		}
		interval--;
	}

	private void timerRanOut() {
		Gdx.input.vibrate(1000);
		timer.cancel();
		running = false;

		mode = (mode + 2) % 2 + 1; // 1->2 , 2->1
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

	public void cancelTimer() {
		if (timer != null)
			timer.cancel();
	}

	public void dispose() {
		cancelTimer();
		myInstance = null;
	}

}
