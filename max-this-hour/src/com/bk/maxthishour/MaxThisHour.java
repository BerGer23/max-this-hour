package com.bk.maxthishour;

import com.badlogic.gdx.ApplicationListener;

public class MaxThisHour implements ApplicationListener {

	public static final String strTag = "MAHinfo";

	@Override
	public void create() {

	}

	@Override
	public void dispose() {
		mthGUI.getInstance().dispose();
	}

	@Override
	public void render() {
		mthGUI.getInstance().render();
	}

	@Override
	public void resize(int width, int height) {
		mthGUI.getInstance().resize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
