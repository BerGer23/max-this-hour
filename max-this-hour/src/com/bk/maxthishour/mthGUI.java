package com.bk.maxthishour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class mthGUI {

	private static mthGUI myInstance;
	private Stage stage;
	private Skin skin;
	private SpriteBatch batch;

	private String[] modes = { "IDLE", "TIME TO WORK", "TIME TO PAUSE",
			"ON HOLD" };
	private String myTimeString = new String();

	// GUI elements
	private Label lblStatus;
	private CheckBox chkMute;
	private Texture txClock;
	private Texture txLogo;
	private Texture txKoala;

	// display variables
	float x, y;

	public static mthGUI getInstance() {
		if (myInstance == null) {
			myInstance = new mthGUI();
		}
		return myInstance;
	}

	private mthGUI() {
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("data/uiskin.json"));

		skin.getFont("default-font").setScale(2.f, 2.f);

		txClock = new Texture(Gdx.files.internal("data/clock.png"));
		txLogo = new Texture(Gdx.files.internal("data/libgdx2.png"));
		txKoala = new Texture(Gdx.files.internal("data/koala.png"));

		// Create a table that fills the screen. Everything else will go inside
		// this table.
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		// mute checkbox
		chkMute = new CheckBox("Mute for work", skin);
		chkMute.setChecked(false);
		chkMute.scale(5f);

		table.add(chkMute);
		table.row();

		lblStatus = new Label("Status: IDLE", skin);
		lblStatus.setAlignment(Align.top | Align.center);
		table.add(lblStatus).width(200f).height(100f).fill().spaceBottom(20);
		table.row();

		// Create a button with the "default" TextButtonStyle. A 3rd parameter
		// can be used to specify a name other than "default".
		final TextButton button = new TextButton("Start/Stop", skin);
		table.add(button).height(150f).width(250f);

		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log(MaxThisHour.strTag,
						"clicked the startstopwork button");
				mthTimer.getInstance().startstopTimer();
				Gdx.input.vibrate(100);
			}
		});

	}

	public void dispose() {
		Gdx.app.log(MaxThisHour.strTag, "disposing");

		Gdx.input.setInputProcessor(null);

		txLogo.dispose();
		txKoala.dispose();

		batch.dispose();
		stage.dispose();
		skin.dispose();

		mthTimer.getInstance().dispose();

		myInstance = null;
	}

	public void render() {
		if (Gdx.input.justTouched())
			Gdx.app.log(MaxThisHour.strTag, "just touched :)");
		switch (mthTimer.getInstance().getMode()) {
		case 0:
			Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); // grey
			break;
		case 1:
			Gdx.gl.glClearColor(0.f, 0.8f, 0.3f, 1); // green
			break;
		case 2:
			Gdx.gl.glClearColor(0.f, 0.f, 1.f, 1);// blue
			break;
		}
		if (mthTimer.getInstance().getMode() > 0) {
			myTimeString = mthUtils.intSecondsToStringMinSec(mthTimer
					.getInstance().getInterval());
		}
		lblStatus.setText((mthTimer.getInstance().getRunning() ? modes[mthTimer
				.getInstance().getMode()] : modes[3])
				+ "\n"
				+ myTimeString
				+ (mthTimer.getInstance().getRunning() ? " left" : ""));

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
		batch.begin();
		// batch.draw(txKoala, x / 2 - txKoala.getWidth() / 2,
		// txLogo.getHeight());
		batch.draw(txClock, x / 2 - txClock.getWidth() / 2,
				y - txClock.getHeight() - y / 20);
		batch.draw(txLogo, x / 2 - txLogo.getWidth() / 2, 0);
		batch.end();
	}

	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	public void resume() {
		Gdx.app.log(MaxThisHour.strTag, "resume");
	}

	public boolean isChkMuteChecked() {
		return chkMute.isChecked();
	}
}
