package com.mygdx.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.controller.NetworkController;
import com.mygdx.game.MyGdxGame;
import com.mygdx.models.SomeRequest;
import com.mygdx.models.SomeResponse;
import com.mygdx.world.Assets;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;


/**
 * Created by Jerem on 22/01/2015.
 */
public class MainMenu implements Screen {


    MyGdxGame game;
    private final BitmapFont font;
    OrthographicCamera guiCam;
    SpriteBatch batcher;
    Rectangle playBounds;
    Rectangle helpBounds;
    Rectangle joinGame;
    Vector3 touchPoint;
    private int width;
    private int height;
    private int bord;
   /* Server server;
    Client client;
*/
    public MainMenu(MyGdxGame game) {
        bord = (320 - 120) / 2;
        this.game = game;
        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320 / 2, 480 / 2, 0);
        batcher = new SpriteBatch();
        playBounds = new Rectangle(bord, 280, 120, 60);
        helpBounds = new Rectangle(bord, 210, 120, 60);
        joinGame = new Rectangle(bord, 140, 120, 60);
        touchPoint = new Vector3();
        font = new BitmapFont();
        font.setColor(Color.ORANGE);
        Gdx.input.setCatchBackKey(false);

      //  game.setId(NetworkController.getInstance().getLocalPeer());

 }

    public void update() {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            System.out.println("Touch Screen");
            System.out.println("X :" + touchPoint.x + ",Y :" + touchPoint.y);

            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Touch Screen GAME");
                System.out.println("X :" + touchPoint.x + ",Y :" + touchPoint.y);
                NetworkController.getInstance().myId=game.id;
                game.setScreen(new GameScreen(game));

                return;
            }
            if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
             //TODO Help Screen
                //game.setScreen(new HelpScreen(game));
                return;
            }
            if (joinGame.contains(touchPoint.x, touchPoint.y)) {
                    System.out.println("Touch Screen Join");
                    System.out.println("X :" + touchPoint.x + ",Y :" + touchPoint.y);
                    NetworkController.getInstance().startEmitter();
                    NetworkController.getInstance().discoverPeers();
                    NetworkController.getInstance().sendMessage("Trouvé");
                    NetworkController.getInstance().sendJoinMessage(game.id);


                System.out.println("Touch Screen GAME");
                    System.out.println("X :" + touchPoint.x + ",Y :" + touchPoint.y);
                    game.setScreen(new GameScreen(game));



                    return;
            }
        }
    }

    long last = System.nanoTime();

    public void draw() {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        batcher.setProjectionMatrix(guiCam.combined);
        batcher.disableBlending();
        batcher.begin();
        batcher.draw(Assets.backgroundRegion, 0, 0, 320, 480);
        int bord = (320 - 120) / 2;
        // System.out.println(bord);
        batcher.draw(Assets.start, bord, 280, 120, 60);
        batcher.draw(Assets.joinGame, bord, 210, 120, 60);
        batcher.draw(Assets.help, bord, 140, 120, 60);
        int log = 320-68;
        batcher.draw(Assets.logo, log, 3,65,50);

        batcher.end();

    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        //     Settings.save();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }



}
