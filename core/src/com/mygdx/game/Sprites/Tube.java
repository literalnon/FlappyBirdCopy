package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.Vector;

/**
 * Created by Donald on 01.06.2017.
 */
public class Tube {

    public static final int FLUTATION = 130;
    public static final int TUBE_GAP = 150;
    public static final int LOWERS_OPENING = 120;

    public static final int TUBE_WIDTH = 52;

    private Texture topTube, bottomTube;
    private Vector2 posBottomTube, posTopTube;
    private Random rand;

    private Rectangle boundsTop, boundsBottom;

    public float gap;

    public Tube(float x){
        gap = 1;
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUTATION) + TUBE_GAP * gap + LOWERS_OPENING);
        posBottomTube = new Vector2(x, posTopTube.y - TUBE_GAP * gap - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBottom = new Rectangle(posBottomTube.x, posBottomTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUTATION) + TUBE_GAP * gap + LOWERS_OPENING);
        posBottomTube.set(x, posTopTube.y - TUBE_GAP * gap - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBottom.setPosition(posBottomTube.x, posBottomTube.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBottom);
    }

    public boolean passed(Rectangle player){
        return Math.abs(posTopTube.x - player.getX()) < 1;
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }
}
