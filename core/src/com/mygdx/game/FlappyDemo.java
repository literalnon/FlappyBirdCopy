package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.States.GameStatesManager;
import com.mygdx.game.States.MenuState;

import java.io.FileReader;
import java.io.IOException;

public class FlappyDemo extends ApplicationAdapter {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Flappy Demo";

	private GameStatesManager manager;

	private SpriteBatch batch;

	private Music backgroundMusic;

	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new GameStatesManager();
		manager.push(new MenuState(manager));

		Gdx.gl.glClearColor(1, 0, 0, 1);

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(0.5f);
		backgroundMusic.play();

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		manager.update(Gdx.graphics.getDeltaTime());
		manager.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		backgroundMusic.dispose();
	}
}
