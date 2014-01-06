package com.bk.maxthishour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class mthGUI {

	static mthGUI myInstance;
	private final Stage stage;
	private final Skin skin;
	// private SpriteBatch batch;

	private final String[] modes = { "IDLE", "TIME TO WORK", "TIME TO PAUSE",
			"ON HOLD" };
	private String myTimeString = new String();

	// GUI elements
	private final Label lblStatus;
	private final CheckBox chkMute;

	// textures

	public static mthGUI getInstance() {
		if (myInstance == null) {
			myInstance = new mthGUI();
		}
		return myInstance;
	}

	public mthGUI() {
		// batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		// A skin can be loaded via JSON or defined programmatically, either is
		// fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region,
		// etc as a drawable, tinted drawable, etc.
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());

		// Configure a TextButtonStyle and name it "default". Skin resources are
		// stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.font.setScale(2.5f);
		skin.add("default", textButtonStyle);

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("default");
		skin.add("default", labelStyle);

		CheckBoxStyle checkboxStyle = new CheckBoxStyle();
		checkboxStyle.checkboxOff = skin.newDrawable("white", Color.DARK_GRAY);
		checkboxStyle.checkboxOn = skin.newDrawable("white", Color.RED);
		checkboxStyle.checked = skin.newDrawable("white", Color.RED);
		checkboxStyle.font = skin.getFont("default");
		skin.add("default", checkboxStyle);

		// Create a table that fills the screen. Everything else will go inside
		// this table.
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		// mute checkbox
		chkMute = new CheckBox("Mute for work", skin);
		chkMute.setChecked(false);

		table.add(chkMute);
		table.row();

		lblStatus = new Label("Status: IDLE", skin);
		lblStatus.setAlignment(Align.top | Align.center);
		table.add(lblStatus).minWidth(200).minHeight(110).fill()
				.spaceBottom(20);
		table.row();

		// Create a button with the "default" TextButtonStyle. A 3rd parameter
		// can be used to specify a name other than "default".
		final TextButton button = new TextButton("Start/Stop", skin);
		table.add(button).height(150f).width(200f);

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
		stage.dispose();
		skin.dispose();
	}

	public void render() {
		switch (mthTimer.getInstance().getMode()) {
		case 0:
			Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); // grey
			break;
		case 1:
			Gdx.gl.glClearColor(0.f, 1.f, 0.f, 1); // green
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
