package com.dprieto.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;


public class TowerDefense extends ApplicationAdapter {

	SpriteBatch batch;

	LevelFactory levelFactory;
	Level level;
	
	@Override
	public void create ()
	{
		Gdx.app.setLogLevel(Application.LOG_DEBUG);


		levelFactory = LevelFactory.getInstance();
		level = LevelFactory.instance.getLevel(1);

		batch = new SpriteBatch();

		if(Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS)
		{
			GestureDetector mobileIm = new GestureDetector(new GestureListener(level));
			Gdx.input.setInputProcessor(mobileIm);
		}
		else if (Gdx.app.getType() == Application.ApplicationType.Desktop)
		{

			InputManagerDesktop desktopIm = new InputManagerDesktop(level);
			Gdx.input.setInputProcessor(desktopIm);


			Pixmap pm = new Pixmap(Gdx.files.internal("GUI/InGame/Cursor.png"));

			Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm,0,0));
			pm.dispose();
		}
	}

	@Override
	public void render ()
	{
		//UPDATE
		Update(Gdx.graphics.getDeltaTime());

		//RENDER
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(level.cameraHelper.camera.combined);
		batch.begin();

		level.render(batch);

		batch.end();
	}

	void Update(float delta)
	{
		level.update(delta);
	}

	@Override
	public void resize(int width, int height)
	{
		level.cameraHelper.resize(width,height);;
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void pause() {
		super.pause();

	}
}
