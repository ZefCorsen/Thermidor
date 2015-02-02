package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.mygdx.models.Peer;
import com.mygdx.screens.MainMenu;
import com.mygdx.screens.TextScreen;
import com.mygdx.world.Assets;
import com.mygdx.world.World;

import java.io.IOException;



public class MyGdxGame extends Game {
    private World world;
    public static int UDP=6969;
    public static int TCP=6970;
    public String id;
    public Peer peer;



    public MyGdxGame() {
    }

    @Override
    public void create() {
        Assets.load();

        setScreen(new TextScreen(this));
        //setScreen(new MainMenu(this));
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    public void setId(String id) {
        this.id = id;
    }
}
