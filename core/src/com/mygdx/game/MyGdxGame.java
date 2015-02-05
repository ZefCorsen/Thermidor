package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.screens.MainMenu;
import com.mygdx.world.Assets;

public class MyGdxGame extends Game {
    public static int UDP = 6969;
    public static int TCP = 6970;
    public String id;

    public MyGdxGame() {

    }

    public void create() {
        Assets.load();


        Gdx.input.getTextInput(new Input.TextInputListener() {

            @Override
            public void input(String texteSaisi) {
                id = texteSaisi;
            }

            @Override
            public void canceled() {
            }
        }, "Bonjour", "Pseudo", "");

        setScreen(new MainMenu(this));
    }

    public void setId(String id) {
        this.id = id;
    }
}
