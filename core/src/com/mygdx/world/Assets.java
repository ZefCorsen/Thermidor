package com.mygdx.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Jerem on 22/01/2015.
 */
public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;
    public static final float CAMERA_WIDTH = 10f;
    public static final float CAMERA_HEIGHT = 7f;
    public static Texture start;
    public static Texture logo;
    public static Texture joinGame;
    public static Texture player;
    public static Texture block;
    public static TextureRegion mainMenu;
    public static Texture help;
    public static TextureRegion ready;
    public static float round = 0.02f;

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }


    private static String prefix = "";

    public static void load() {
        background = loadTexture(prefix + "images/arriereplan.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);

        logo = loadTexture(prefix + "images/logo.png");
        start = loadTexture(prefix + "images/buton.png");
        help = loadTexture(prefix + "images/buton3.png");
        block = loadTexture(prefix+"images/block.png");
        joinGame = loadTexture(prefix + "images/buton2.png");
        player = loadTexture(prefix + "images/bonhomme.png");
        mainMenu = new TextureRegion(start);
        ready = new TextureRegion(start, 320, 224, 192, 32);

    }


}
