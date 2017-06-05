package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FlappyDemo;
import com.mygdx.game.Sprites.Bird;
import com.mygdx.game.Sprites.Tube;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import sun.rmi.runtime.Log;

/**
 * Created by Donald on 31.05.2017.
 */
public class PlayState extends State {

    public static final int TUBE_SPACING = 125;
    public static final int TUBE_COUNT = 4;
    public static final int GROUND_OFFSET = -60;
    public static final int PREVIEW = 3;

    private Bird bird;
    private Texture background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Array<Tube> tubes;
    private BitmapFont font;
    private int points;
    private float time;

    public PlayState(GameStatesManager manager) {
        super(manager);

        background = new Texture("bg.png");
        ground = new Texture("ground.png");

        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_OFFSET);
        groundPos2 = new Vector2(camera.position.x - camera.viewportWidth / 2 + ground.getWidth(), GROUND_OFFSET);

        bird = new Bird(50, 300, ground.getHeight() + GROUND_OFFSET);

        tubes = new Array<Tube>();

        for(int i = PREVIEW; i < TUBE_COUNT + PREVIEW; ++i){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        camera.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);

        points = 0;
        font = new BitmapFont();
        time = 4.0f;
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        camera.position.x = bird.getPosition().x + 80;

        for (int i = 0; i < TUBE_COUNT; ++i){
            Tube tube = tubes.get(i);

            if(camera.position.x - camera.viewportWidth / 2 >
                    (tube.getPosTopTube().x + tube.getTopTube().getWidth())){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if(tube.collides(bird.getBounds())) {
                if(manager.maxPoints < points){
                    try{
                        FileWriter writer = new FileWriter("points.txt");
                        writer.write(points);
                        writer.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    manager.maxPoints = points;
                }

                manager.set(new GameOverState(manager));

            }else if(tube.passed(bird.getBounds())){
                points++;
            }

            if(tube.gap < 0.5f)
                tube.gap = 1.1f;
            else
                tube.gap -= dt;
        }
        if(time > 0)
            time -= dt;

        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        spriteBatch.draw(background, camera.position.x - camera.viewportWidth / 2, 0);

        spriteBatch.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);

        for(Tube tube : tubes){
            spriteBatch.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            spriteBatch.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }

        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);

        font.draw(spriteBatch, "Points: " + points, camera.position.x - 120, ground.getHeight() + GROUND_OFFSET - 25);
        if(time >= 0)
            font.draw(spriteBatch, (int) time + "", camera.position.x, camera.position.y);

        font.draw(spriteBatch, "max : " + manager.maxPoints, camera.position.x, ground.getHeight() + GROUND_OFFSET - 25);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        bird.dispose();
        background.dispose();
        for (Tube tube : tubes){
            tube.dispose();
        }
        ground.dispose();
    }

    public void updateGround(){
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
