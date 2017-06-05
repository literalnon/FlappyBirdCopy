package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.States.PlayState;

import java.util.Vector;

/**
 * Created by Donald on 31.05.2017.
 */
public class Bird {
    public static final int  MOVEMENT  = 100;
    public static final int GRAVITY = -15;
    private static final int JUMP_HEIGHT = 350;

    private Vector3 velosity;
    private Vector3 position;

    private Texture bird;
    private Rectangle bounds;
    private Animation animation;
    private Sound flap;
    private int floor;

    public Bird(int x, int y, int floor){
        this.floor = floor;
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);
        bird = new Texture("birdanimation.png");
        animation = new Animation(new TextureRegion(bird), 3, 0.5f);
        bounds = new Rectangle(x, y, bird.getWidth() / 3, bird.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float delta){
        animation.update(delta);
        if(position.y > 0)
            velosity.add(0, GRAVITY, 0);

        velosity.add(0, GRAVITY, 0);
        velosity.scl(delta);
        position.add(MOVEMENT * delta, velosity.y, 0);

        if(position.y < floor)
            position.y = floor;

        velosity.scl(1 / delta);

        bounds.setPosition(position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void jump(){
        velosity.y = JUMP_HEIGHT;
        flap.play();
    }

    public void dispose(){
        bird.dispose();
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return animation.getFrame();
    }
}
