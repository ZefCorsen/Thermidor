package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.screens.MainMenu;
import com.mygdx.world.Assets;
import com.mygdx.world.World;

import java.io.IOException;



public class MyGdxGame extends Game {
    private World world;
    public static int UDP=6969;
    public static int TCP=6970;



    public MyGdxGame() {
    }

    @Override
    public void create() {
        Assets.load();


        setScreen(new MainMenu(this));
    }



}
