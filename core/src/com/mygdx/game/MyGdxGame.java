package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.screens.GameScreen;
import com.mygdx.world.World;

public class MyGdxGame extends Game {
    private World world;


    @Override
    public void create() {
        setScreen(new GameScreen());
    }

}
