package com.mygdx.screens;


/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.controller.MondeControlleur;
import com.mygdx.controller.NetworkController;
import com.mygdx.game.MyGdxGame;
import com.mygdx.player.Player;
import com.mygdx.world.Assets;
import com.mygdx.world.MondeRenderTexture;
import com.mygdx.world.WorldImpl;

import java.math.BigInteger;
import java.security.SecureRandom;

public class GameScreen implements Screen, InputProcessor {

    private MyGdxGame game;
    private MondeRenderTexture mondeRender;
    private MondeControlleur controller;
    private int width, height;
    private WorldImpl worldImpl;
    private Player player1;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private Rectangle musketBound, bombBound;

    public GameScreen(MyGdxGame game) {
        this.game = game;
        if (game.id == null || game.id.isEmpty()) {
            SecureRandom random = new SecureRandom();
            game.id = (new BigInteger(130, random)).toString(32);
        }

        spriteBatch = new SpriteBatch();
        Gdx.input.setCatchBackKey(true);
        worldImpl = new WorldImpl();
        mondeRender = new MondeRenderTexture(worldImpl, spriteBatch);
        controller = new MondeControlleur(worldImpl);
        musketBound = new Rectangle(Assets.actionBordX, Assets.actionBordY, Assets.tailleActionX, Assets.tailleActionY);
        bombBound = new Rectangle(Assets.actionBordX, Assets.actionBordY + Assets.tailleActionY, Assets.tailleActionX, Assets.tailleActionY);

        player1 = new Player(100 / Assets.PIXELS_TO_METERS, 110 / Assets.PIXELS_TO_METERS, worldImpl);
        player1.setId(game.id);


        camera = new OrthographicCamera(Gdx.graphics.getWidth() / Assets.PIXELS_TO_METERS, Gdx.graphics.
                getHeight() / Assets.PIXELS_TO_METERS);
        NetworkController.getInstance().startReceiver(worldImpl);
        if (NetworkController.getInstance().myWorld == null) {
            NetworkController.getInstance().myWorld = worldImpl;
        }

        Gdx.input.setInputProcessor(this);
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        worldImpl.getWorld().step(1f / 60f, 6, 2);
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        controller.update(delta);
        mondeRender.render();
        spriteBatch.end();
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
        Gdx.input.setInputProcessor(null);
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        worldImpl.getWorld().dispose();

        // TODO Auto-generated method stub
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.BACK) {
            this.dispose();
            game.setScreen(new MainMenu(game));

        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float x, y;
        x = (screenX - width / 2) / Assets.PIXELS_TO_METERS;
        y = (height - screenY - height / 2) / Assets.PIXELS_TO_METERS;
        if (bombBound.contains(x, y)) {
            //TODO dispose Bombe

            System.out.println("Touche Bombe");
        } else if (musketBound.contains(x, y)) {
            //TODO dispose musket

            System.out.println("Touche Musket");
        } else {


            controller.setPlayerInPosition(game.id, x, y);
        }
        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float x, y;
        x = (screenX - width / 2) / Assets.PIXELS_TO_METERS;
        y = (height - screenY - height / 2) / Assets.PIXELS_TO_METERS;

        if (bombBound.contains(x, y)) {
            //TODO dispose Bombe
        } else if (musketBound.contains(x, y)) {
            //TODO dispose musket
        } else {
            controller.setPlayerInPosition(game.id, x, y);
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;

    }
}
