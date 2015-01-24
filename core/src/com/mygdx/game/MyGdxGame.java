package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.mygdx.screens.MainMenu;
import com.mygdx.world.Assets;
import com.mygdx.world.World;

import org.jgroups.JChannel;

public class MyGdxGame extends Game {
    private World world;



    @Override
    public void create() {
        Assets.load();
        JChannel
        setScreen(new MainMenu(this));
    }


}
