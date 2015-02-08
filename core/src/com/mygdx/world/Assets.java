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
    public static final float PLAYER_SPEED = 2.5f;
    public static final float PIXELS_TO_METERS = 100f;
    public static Texture backgroundMain, backgroundGame;
    public static TextureRegion backgroundRegionMain, backgroundRegionGame;
    public static Texture start, logo, joinGame, help;

    public static Texture player;
    public static Texture musket, bomb;
    public static Texture bullet, bombGame;
    public static TextureRegion mainMenu;

    public static float round = 0.001f;
    public static BitmapFont buton;
    public static float SIZE = 0.03f;
    public static Sprite sprite, spriteBomb, spriteBullet;

    public static float ppuX = Gdx.graphics.getWidth();
    public static float ppuY = Gdx.graphics.getHeight();
    public static final short PHYSICS_ENTITY = 0x0001;    // 0001
    public static final short WORLD_ENTITY = 0x0004;
    public static final short BOMB_ENTITY = 0x0002;

    //public static final short MASK_PLAYER = -1; // or ~CATEGORY_PLAYER
    // public static final short MASK_BULLET = -1; // or ~CATEGORY_MONSTER
    public static final short MASK_PLAYER = BOMB_ENTITY | WORLD_ENTITY | PHYSICS_ENTITY; // or ~CATEGORY_PLAYER
    public static final short MASK_BULLET = PHYSICS_ENTITY | WORLD_ENTITY | BOMB_ENTITY; // or ~CATEGORY_MONSTER
    public static final short MASK_WORLD = -1;


    public static float widthPlayer = (SIZE * ppuX) / Assets.PIXELS_TO_METERS;
    public static float heightPlayer = (SIZE * ppuY) / Assets.PIXELS_TO_METERS;

    public static float tailleActionX = (Assets.ppuX / Assets.PIXELS_TO_METERS) / 8;
    public static float tailleActionY = (Assets.ppuY / Assets.PIXELS_TO_METERS) / 5;
    public static float actionBordX = (Assets.ppuX / Assets.PIXELS_TO_METERS) / 2 - tailleActionX;
    public static float actionBordY = -(Assets.ppuY / Assets.PIXELS_TO_METERS) / 2;


    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }


    private static String prefix = "";

    public static void load() {
        backgroundMain = loadTexture(prefix + "images/MainBackground.jpg");
        // backgroundGame = loadTexture(prefix + "images/herbe2.png");
        // backgroundGame = loadTexture(prefix + "images/herbe4.png");
        backgroundGame = loadTexture(prefix + "images/herbe5.png");
        backgroundRegionMain = new TextureRegion(backgroundMain);
        backgroundRegionGame = new TextureRegion(backgroundGame);
        buton = new BitmapFont();
        logo = loadTexture(prefix + "images/logo.png");
        start = loadTexture(prefix + "images/buton.png");
        help = loadTexture(prefix + "images/buton3.png");

        joinGame = loadTexture(prefix + "images/buton2.png");
        //player = loadTexture(prefix + "images/bonhomme.png");
        player = loadTexture(prefix + "images/fanTran.png");
        bullet = loadTexture(prefix + "images/bullet.gif");
        musket = loadTexture(prefix + "images/musket.gif");
        bomb = loadTexture(prefix + "images/poudre.gif");

        bombGame = loadTexture(prefix + "images/poudreGame.gif");

        mainMenu = new TextureRegion(start);
        sprite = new Sprite(player);
        heightPlayer = (widthPlayer * sprite.getHeight()) / sprite.getWidth();
        sprite.setSize(widthPlayer, heightPlayer);


        spriteBomb = new Sprite(bombGame);
        float heightBomb = heightPlayer / 2;
        float widthBomb = (heightBomb * spriteBomb.getWidth()) / spriteBomb.getHeight();

        spriteBomb.setSize(widthBomb, heightBomb);

        spriteBullet = new Sprite(bullet);
        //heightBomb= heightPlayer/2;
        //widthBomb = (heightBomb * spriteBullet.getWidth()) / spriteBullet.getHeight();

        spriteBullet.setSize(widthBomb / 5, heightBomb / 5);

    }


}
