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
import com.badlogic.gdx.math.Vector2;
import com.mygdx.controller.MondeControlleur;
import com.mygdx.controller.NetworkController;
import com.mygdx.game.MyGdxGame;
import com.mygdx.models.ItemMessage;
import com.mygdx.models.PositionMessage;
import com.mygdx.player.Bomb;
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
    private Player player1, player2;
    private Bomb bomb1;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private Rectangle musketBound, bombBound;

    public GameScreen(MyGdxGame game) {
        this(game, WorldImpl.getInstance());

//        player2 = new Player(-100 / Assets.PIXELS_TO_METERS, -110 / Assets.PIXELS_TO_METERS, worldImpl, game.id);
    }

    public GameScreen(MyGdxGame game, WorldImpl worldImpl) {
        this.game = game;
        if (game.id == null || game.id.isEmpty()) {
            SecureRandom random = new SecureRandom();
            game.id = (new BigInteger(130, random)).toString(32);
        }

        spriteBatch = new SpriteBatch();
        Gdx.input.setCatchBackKey(true);
        this.worldImpl = worldImpl;
        mondeRender = new MondeRenderTexture(spriteBatch ,game.id);
        controller = new MondeControlleur();
        bombBound = new Rectangle(Assets.actionBordX, Assets.actionBordY, Assets.tailleActionX, Assets.tailleActionY);
        musketBound = new Rectangle(Assets.actionBordX, Assets.actionBordY + Assets.tailleActionY, Assets.tailleActionX, Assets.tailleActionY);
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / Assets.PIXELS_TO_METERS, Gdx.graphics.
                getHeight() / Assets.PIXELS_TO_METERS);

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
        if (keycode == Input.Keys.SPACE) {
            NetworkController.getInstance().sendToAll(new ItemMessage(game.id,1));
            controller.createBullet(game.id);
        } else if (keycode == Input.Keys.F) {
            NetworkController.getInstance().sendToAll(new ItemMessage(game.id,2));
            controller.createBomb(game.id);
        }
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
            NetworkController.getInstance().sendToAll(new ItemMessage(game.id,2));
            controller.createBomb(game.id);
        } else if (musketBound.contains(x, y)) {

            //TODO dispose musket

            System.out.println("Touche Musket");
            NetworkController.getInstance().sendToAll(new ItemMessage(game.id,1));
            controller.createBullet(game.id);
        } else {
            NetworkController.getInstance().sendToAll(new PositionMessage(game.id,new Vector2(x,y)));
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
        NetworkController.getInstance().sendToAll(new PositionMessage(game.id,new Vector2(x,y)));
        controller.setPlayerInPosition(game.id, x, y);

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
