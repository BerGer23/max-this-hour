package com.bk.maxthishour;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "max-this-hour";
		cfg.useGL20 = false;
		cfg.width = 384;
		cfg.height = 592;

		new LwjglApplication(new MaxThisHour(), cfg);
	}
}
