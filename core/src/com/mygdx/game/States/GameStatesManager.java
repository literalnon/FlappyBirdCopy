package com.mygdx.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by Donald on 31.05.2017.
 */
public class GameStatesManager {
    private Stack<State> states;
    public int maxPoints;

    public GameStatesManager(){
        states = new Stack<State>();

        try {
            FileReader fileReader = new FileReader("points.txt");

            if((maxPoints = fileReader.read()) == -1)
                maxPoints = 1;
            fileReader.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    public void update(float delta){
        states.peek().update(delta);
    }

    public void render(SpriteBatch batch){
        states.peek().render(batch);
    }
}
