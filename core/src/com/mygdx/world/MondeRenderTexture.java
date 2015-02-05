package com.mygdx.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.player.Player;

/**
 * Created by Jerem on 21/01/2015.
 */
public class MondeRenderTexture {


    private WorldImpl world;
    private SpriteBatch spriteBatch;


    public MondeRenderTexture(WorldImpl world, SpriteBatch spriteBatch) {
        this.world = world;
      this.spriteBatch = spriteBatch;

    }

    public void render() {
        spriteBatch.draw(Assets.backgroundRegionGame, -Assets.ppuX/2, -Assets.ppuY/2, Assets.ppuX, Assets.ppuY);
        drawPlayer();

    }


    public void drawPlayer() {
        for (Player player : world.getPlayers()) {
            spriteBatch.draw(Assets.player, (player.getBody().getPosition().x - Assets.sprite.getWidth() / 2), (player.getBody().getPosition().y - Assets.sprite.getHeight() / 2), Assets.SIZE * Assets.ppuX, Assets.SIZE * Assets.ppuY);
        }
    }

}