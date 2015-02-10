package com.mygdx.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.controller.MondeControlleur;
import com.mygdx.controller.NetworkController;
import com.mygdx.game.MyGdxGame;
import com.mygdx.world.Assets;
import com.mygdx.world.WorldImpl;


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
        bord = -60;
        this.game = game;
        guiCam = new OrthographicCamera(Assets.ppuY, Assets.ppuX);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(guiCam.combined);
        playBounds = new Rectangle(bord, -60, 120, 60);
        helpBounds = new Rectangle(bord, -130, 120, 60);
        joinGame = new Rectangle(bord, -200, 120, 60);
        touchPoint = new Vector3();
        font = new BitmapFont();
        font.setColor(Color.ORANGE);
        Gdx.input.setCatchBackKey(false);

    }

    public void update() {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Click on Play");
                NetworkController.getInstance().startReceiver();
                game.setScreen(new GameScreen(game));
                try {
                    MondeControlleur.getInstance().addPlayerToWorld(game.id);

                    MondeControlleur.getInstance().addPlayerToWorld("Test");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return;
            }
            if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Click on Help");
                //TODO Help Screen
                return;
            }
            if (joinGame.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Click on Join");
                try {
                    WorldImpl.getInstance();
                    NetworkController.getInstance().discoverPeers();
                    NetworkController.getInstance().startReceiver();
                    NetworkController.getInstance().startEmitters();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (NetworkController.worldIsLoad()) {
                    game.setScreen(new GameScreen(game));
                }

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
        batcher.draw(Assets.backgroundRegionMain, -Assets.ppuY / 2, -Assets.ppuX / 2, Assets.ppuY, Assets.ppuX);
        int bord = -60;
        batcher.draw(Assets.start, bord, -60, 120, 60);
        batcher.draw(Assets.joinGame, bord, -130, 120, 60);
        batcher.draw(Assets.help, bord, -200, 120, 60);
        float logX = 240 - 60.5f;
        float logY = -419;
        batcher.draw(Assets.logo, logX, logY, 60, 80);

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
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }


}
