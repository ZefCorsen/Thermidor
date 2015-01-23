package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.screens.MainMenu;
import com.mygdx.world.Assets;
import com.mygdx.world.World;

public class MyGdxGame extends Game {
    private World world;

<<<<<<< HEAD
=======
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.setScale(12);
        
	}
>>>>>>> origin/Sprint-1

    @Override
    public void create() {
        Assets.load();
        setScreen(new MainMenu(this));
    }

<<<<<<< HEAD
=======
    @Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        if (Gdx.input.isTouched()) {
            i++;
            font.draw(batch, "hello"+i, 200, 200);
        }
        batch.end();
    }


    public void refresh() {
        font.draw(batch, "GUY", 50, 100);

    }
>>>>>>> origin/Sprint-1
}
