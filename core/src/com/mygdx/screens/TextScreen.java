package com.mygdx.screens;

/**
 * Created by Jerem on 02/02/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.MyGdxGame;
import com.mygdx.world.Assets;

import static com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;


public class TextScreen implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    MyGdxGame game;
    Texture background;
    TextButton button;

    public TextScreen (MyGdxGame game){this.game = game;}
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpriteBatch sprite = new SpriteBatch();
        sprite.begin();
        sprite.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//corrige sans caméra !!
        sprite.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        skin = new Skin();
        Drawable redDrawable = skin.newDrawable("test", Color.BLUE);
                //new Drawable("whiteRegion", Color.RED);
        Drawable blueDrawable = skin.newDrawable("whiteRegion", Color.BLUE);

        TextButtonStyle buttonStyle = new TextButtonStyle(redDrawable,blueDrawable,redDrawable, Assets.buton);

        stage = new Stage();
        button = new TextButton("Click me!", buttonStyle);

        button.setWidth(200f);
        button.setHeight(20f);
        button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 10f);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                button.setText("You clicked the button");
            }
        });

        stage.addActor(button);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
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
        // TODO Auto-generated method stub
    }
}

