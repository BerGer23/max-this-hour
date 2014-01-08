package com.bk.maxthishour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class mthGUI {

	private static mthGUI myInstance;
	private final Stage stage;
	private final Skin skin;
	private final SpriteBatch batch;

	private final String[] modes = { "IDLE", "TIME TO WORK", "TIME TO PAUSE",
			"ON HOLD" };
	private String myTimeString = new String();

	// GUI elements

	private final Label lblStatus;
	private final CheckBox chkMute;

	private final Slider sliVibrateLength;
	private final Label lblVibrateLength;
	private final int iVibMsPerTick = 1000;
	private int iMsToVibrate;

	private final Texture txClock;
	private final Texture txLogo;
	private final Texture txKoala;

	// textures
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
		Gdx.app.log(MaxThisHour.strTag, x + " " + y);

		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("data/uiskin.json"));

		skin.getFont("default-font").setScale(x / 400, y / 600);

		txClock = new Texture(Gdx.files.internal("data/clock.png"));
		txLogo = new Texture(Gdx.files.internal("data/libgdx2.png"));
		txKoala = new Texture(Gdx.files.internal("data/koala.png"));

		// Create a table that fills the screen. Everything else will go inside
		// this table.
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		// mute checkbox
		chkMute = new CheckBox("Mute phonecalls", skin);
		chkMute.setChecked(false);
		chkMute.getCells().get(0).size((x + y) / 35);
		chkMute.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.log(MaxThisHour.strTag,
						"clicked the checkbox for muting");
				if (!chkMute.isChecked())
					mthUtils.Mute();
				else
					mthUtils.Unmute();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		table.add(chkMute).width(y / 15).spaceBottom(x / 200);
		table.row();

		lblStatus = new Label("Status: IDLE", skin);
		lblStatus.setAlignment(Align.center);
		table.add(lblStatus).width(y / 4).height(x / 10).fill()
				.spaceBottom(x / 400);
		table.row();

		sliVibrateLength = new Slider(0.1f, 5f, 0.1f, false, skin);
		sliVibrateLength.setValue(1f);
		sliVibrateLength.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				lblVibrateLength
						.setText("Move slider to adjust vibrate length: "
								+ updateMsToVibrate() + " ms");
			}
		});

		table.add(sliVibrateLength).width(y / 4).height(x / 10).fill();
		table.row();

		lblVibrateLength = new Label("Move slider to adjust vibrate length: "
				+ updateMsToVibrate() + " ms", skin);
		lblVibrateLength.setAlignment(Align.top | Align.center);
		table.add(lblVibrateLength).width(y / 4).height(x / 10).fill()
				.spaceBottom(10);
		table.row();

		// Create a button with the "default" TextButtonStyle. A 3rd parameter
		// can be used to specify a name other than "default".
		final TextButton button = new TextButton("Start/Stop", skin);
		table.add(button).height(y / 9).width(x / 2);

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
		batch.draw(txClock, x / 2 - Gdx.graphics.getWidth() / 10, y
				- (Gdx.graphics.getHeight() / 8) - y / 20,
				Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 8);
		batch.draw(txLogo, x / 2 - Gdx.graphics.getWidth() / 8, 0,
				Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
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

	public int updateMsToVibrate() {
		iMsToVibrate = (int) (sliVibrateLength.getValue() * iVibMsPerTick);
		return iMsToVibrate;
	}
}
