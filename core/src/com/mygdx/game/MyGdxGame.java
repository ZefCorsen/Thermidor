package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    private BitmapFont font;
	
	@Override
	public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.setScale(12);
	}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        font.draw(batch, "Hello World", 200, 200);
        batch.end();

    }
}
