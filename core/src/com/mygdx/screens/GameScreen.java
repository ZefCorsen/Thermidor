package com.mygdx.screens;

/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.controller.MondeControlleur;
import com.mygdx.world.MondeRenderTexture;
import com.mygdx.world.World;

public class GameScreen implements Screen, InputProcessor {

    private World monde;
    // private MondeRender mondeRender;
    private MondeRenderTexture mondeRender;
    private MondeControlleur controller;
    private int width, height;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.update(delta);
        mondeRender.render();
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
        //mondeRender = new MondeRender(monde);
        mondeRender = new MondeRenderTexture(monde, true);
        controller = new MondeControlleur(monde);
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
        switch (keycode) {
            case (Input.Keys.LEFT):
                controller.leftPressed();
                break;
            case (Input.Keys.RIGHT):
                controller.rightPressed();
                break;
            case (Input.Keys.Z):
                controller.jumpPressed();
                break;
            case (Input.Keys.S):
                controller.firePressed();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case (Input.Keys.LEFT):
                controller.leftReleased();
                break;
            case (Input.Keys.RIGHT):
                controller.rightReleased();
                break;
            case (Input.Keys.Z):
                controller.jumpReleased();
                break;
            case (Input.Keys.S):
                controller.fireReleased();
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (screenX < width / 2 && screenY > height / 2) {
            controller.leftPressed();
        }
        if (screenX > width / 2 && screenY > height / 2) {
            controller.rightPressed();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (screenX < width / 2 && screenY > height / 2) {
            controller.leftReleased();
        }
        if (screenX > width / 2 && screenY > height / 2) {
            controller.rightReleased();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (screenX < width / 2 && screenY > height / 2) {
            controller.leftPressed();
        }
        if (screenX > width / 2 && screenY > height / 2) {
            controller.rightPressed();
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
