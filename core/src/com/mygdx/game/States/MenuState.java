package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.FlappyDemo;

import sun.rmi.runtime.Log;

/**
 * Created by Donald on 31.05.2017.
 */
public class MenuState extends State {

    private Texture background;
    private Texture playBtn;

    public MenuState(GameStatesManager manager) {
        super(manager);
        camera.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            manager.set(new PlayState(manager));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        spriteBatch.draw(background, 0, 0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
        spriteBatch.draw(playBtn, camera.position.x - playBtn.getWidth() / 2, camera.position.y);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
