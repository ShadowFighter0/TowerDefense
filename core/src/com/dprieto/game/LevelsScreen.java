package com.dprieto.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

public class LevelsScreen implements Screen {

	MainGame game;
	public static LevelsScreen instance;

	SpriteBatch batch;

	LevelFactory levelFactory;
	SoundManager soundManager;

	Level level;

	//Input Level
	InputManagerLevel desktopIMLevel;
	GestureListener mobileIMLevel;

	public LevelsScreen (MainGame game)
	{
		this.game = game;
		instance = this;

		levelFactory = LevelFactory.getInstance();
		levelFactory.setMasterClass(this);

		soundManager = soundManager.getInstance();

		batch = new SpriteBatch();

		desktopIMLevel = new InputManagerLevel();
		mobileIMLevel = new GestureListener();
	}

	public void SetLevel (int levelIndex)
	{
		this.level = levelFactory.getLevel(levelIndex);

		SoundManager.getInstance().PlayMusic();

		mobileIMLevel.SetLevel(level);
		desktopIMLevel.SetLevel(level);

		if(Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS)
		{
			Gdx.input.setInputProcessor(new GestureDetector(mobileIMLevel));
		}
		else if (Gdx.app.getType() == Application.ApplicationType.Desktop)
		{
			Gdx.input.setInputProcessor(desktopIMLevel);
		}
	}

	@Override
	public void show() {

	}

	void Update(float delta)
	{
		level.update(delta);
	}

	@Override
	public void render(float delta) {
		//UPDATE
		Update(delta);

		//RENDER
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		level.render(batch);
	}

	@Override
	public void resize(int width, int height)
	{
		level.worldCamera.ExpandResize(width,height);
		level.guiCamera.ExpandResize(width,height);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}
}
