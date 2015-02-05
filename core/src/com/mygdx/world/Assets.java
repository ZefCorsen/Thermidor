package com.mygdx.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Jerem on 22/01/2015.
 */
public class Assets {
    public static final float PLAYER_SPEED = 4f;
    public static final float PIXELS_TO_METERS = 100f;
    public static Texture backgroundMain;
    public static TextureRegion backgroundRegionMain;
    public static Texture backgroundGame;
    public static TextureRegion backgroundRegionGame;
    public static Texture start;
    public static Texture logo;
    public static Texture joinGame;
    public static Texture player;
    public static Texture block;
    public static TextureRegion mainMenu;
    public static Texture help;
    public static float round = 0.001f;
    public static BitmapFont buton;
    public static float SIZE = 0.06f;
    public static Sprite sprite;
    public static float ppuX = Gdx.graphics.getWidth();
    public static float ppuY = Gdx.graphics.getHeight();
    public static final short PHYSICS_ENTITY = 0x1;    // 0001
    public static final short WORLD_ENTITY = 0x1 << 1; // 0010 or 0x2 in hex


    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }


    private static String prefix = "";

    public static void load() {
        backgroundMain = loadTexture(prefix + "images/MainBackground.jpg");
        backgroundGame = loadTexture(prefix + "images/arriereplan.png");
        backgroundRegionMain = new TextureRegion(backgroundMain);
        backgroundRegionGame = new TextureRegion(backgroundGame);
        buton = new BitmapFont();
        logo = loadTexture(prefix + "images/logo.png");
        start = loadTexture(prefix + "images/buton.png");
        help = loadTexture(prefix + "images/buton3.png");
        block = loadTexture(prefix + "images/block.png");
        joinGame = loadTexture(prefix + "images/buton2.png");
        player = loadTexture(prefix + "images/bonhomme.png");
        mainMenu = new TextureRegion(start);
        sprite = new Sprite(player);
        sprite.setSize((SIZE * ppuX)/Assets.PIXELS_TO_METERS, (SIZE * ppuY)/Assets.PIXELS_TO_METERS);

    }


}
