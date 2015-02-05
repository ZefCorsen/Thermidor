package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.models.Peer;
import com.mygdx.screens.MainMenu;
import com.mygdx.world.Assets;
import com.mygdx.world.World;




public class MyGdxGame extends Game {
    private World world;
    public static int UDP=6969;
    public static int TCP=6970;
    public String id ;
    public Peer peer;


    boolean block = true;

    public MyGdxGame() {

    }

    public void create() {
        Assets.load();


            Gdx.input.getTextInput(new Input.TextInputListener() {

                @Override
                public void input(String texteSaisi) {
                    id=texteSaisi;
                }

                @Override
                public void canceled() {
                }
            }, "Bonjour", "Pseudo", "");

        setScreen(new MainMenu(this));
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    public void setId(String id) {
        this.id = id;
    }
}
