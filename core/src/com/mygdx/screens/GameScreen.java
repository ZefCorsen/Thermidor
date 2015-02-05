package com.mygdx.screens;


/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.controller.MondeControlleur;
import com.mygdx.controller.NetworkController;
import com.mygdx.game.MyGdxGame;
import com.mygdx.player.Player;
import com.mygdx.world.Assets;
import com.mygdx.world.MondeRenderTexture;
import com.mygdx.world.World;

public class GameScreen implements Screen, InputProcessor {

    private World monde;
    private MyGdxGame game;
    private MondeRenderTexture mondeRender;
    private MondeControlleur controller;
    private int width, height;

    public GameScreen(MyGdxGame game) {
        this.game = game;
        Gdx.input.setCatchBackKey(true);


    }

    @Override
    public void render(float delta) {
        monde=NetworkController.getInstance().myWorld;
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.update(delta);
        mondeRender.render();
        NetworkController.getInstance().myWorld=monde;
    }

    @Override
    public void resize(int width, int height) {
        mondeRender.setSize(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void show() {
        monde = new World();
        NetworkController.getInstance().startReceiver(monde);
        mondeRender = new MondeRenderTexture(monde, false);
        controller = new MondeControlleur(monde);
        if (NetworkController.getInstance().myWorld==null){
            NetworkController.getInstance().myWorld=monde;
        }

        Player player = new Player(new Vector2(1, 1), game.id);
        monde.addPlayer(player);
        Gdx.input.setInputProcessor(this);
        // TODO Auto-generated method stub
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
        x = screenX / (width / Assets.CAMERA_WIDTH);
        y = (height - screenY) / (height / Assets.CAMERA_HEIGHT);
        controller.setPlayerInPosition(game.id ,x, y);
        NetworkController.getInstance().sendPosition(game.id);

        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float x, y;
        x = screenX / (width / Assets.CAMERA_WIDTH);
        y = (height - screenY) / (height / Assets.CAMERA_HEIGHT);
        controller.setPlayerInPosition(game.id, x, y);
        NetworkController.getInstance().sendPosition(game.id);


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
