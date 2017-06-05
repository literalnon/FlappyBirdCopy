package com.mygdx.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.Vector;

/**
 * Created by Donald on 31.05.2017.
 */
public abstract class State {

    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected  GameStatesManager manager;

    public State(GameStatesManager manager){
        this.manager = manager;
        camera = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch spriteBatch);
    public abstract void dispose();
}
