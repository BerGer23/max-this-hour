package com.bk.maxthishour;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class MaxThisHour implements ApplicationListener {

	public static String strTag;
	private static SpecialFeatures mySpecialFeatureObject;

	public MaxThisHour() {
		// TODO Auto-generated constructor stub
	}

	public MaxThisHour(SpecialFeatures specialFeatureObject) {
		mySpecialFeatureObject = specialFeatureObject;
	}

	@Override
	public void create() {
		strTag = "MAHinfo";
	}

	@Override
	public void dispose() {
		mthGUI.getInstance().dispose();
		strTag = null;
		Gdx.app.exit();
		Gdx.app.log(MaxThisHour.strTag, "now that worked well");
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
		mthGUI.getInstance().resume();
	}

	public static SpecialFeatures getMySpecialFeatureObject() {
		return mySpecialFeatureObject;
	}
}
