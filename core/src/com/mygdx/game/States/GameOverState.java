package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.FlappyDemo;

/**
 * Created by Donald on 31.05.2017.
 */
public class GameOverState extends State {

    private Texture background;
    private Texture gameover;

    public GameOverState(GameStatesManager manager) {
        super(manager);
        camera.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        gameover = new Texture("gameover.png");
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
        spriteBatch.draw(gameover, camera.position.x - gameover.getWidth() / 2, camera.position.y);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameover.dispose();
    }
}
