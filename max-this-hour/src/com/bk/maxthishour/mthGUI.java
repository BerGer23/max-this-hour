package com.bk.maxthishour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
	private Stage stage;
	private Skin skin;
	private SpriteBatch batch;
	private String[] modes = { "IDLE", "TIME TO WORK", "TIME TO PAUSE" };

	// GUI elements
	private Label lblStatus;

	public static mthGUI getInstance() {
		if (myInstance == null) {
			myInstance = new mthGUI();
		}
		return myInstance;
	}

	public mthGUI() {
		batch = new SpriteBatch();
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

		// Create a table that fills the screen. Everything else will go inside
		// this table.
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		lblStatus = new Label("Status: IDLE", skin);
		lblStatus.setAlignment(Align.top | Align.center);
		table.add(lblStatus).minWidth(200).minHeight(110).fill();

		// Create a button with the "default" TextButtonStyle. A 3rd parameter
		// can be used to specify a name other than "default".
		final TextButton button = new TextButton("Start Timer", skin);
		table.add(button).height(150f).width(200f);

		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log(MaxThisHour.strTag, "clicked the startwork button");
				mthTimer.getInstance().startTimer();
				Gdx.input.vibrate(1000);
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
			Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
			break;
		case 1:
			Gdx.gl.glClearColor(0.f, 1.f, 0.f, 1);
			break;
		case 2:
			Gdx.gl.glClearColor(0.f, 0.f, 1.f, 1);
			break;
		}
		lblStatus.setText("Current Mode: "
				+ modes[mthTimer.getInstance().getMode()]);

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
	}

	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}
}
