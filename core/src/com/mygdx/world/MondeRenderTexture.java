package com.mygdx.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.player.Bomb;
import com.mygdx.player.Bullet;
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
      spriteBatch.enableBlending();

    }

    public MondeRenderTexture(SpriteBatch spriteBatch) {
        this.world = WorldImpl.getInstance();
        this.spriteBatch = spriteBatch;
        spriteBatch.enableBlending();

    }

    public void render() {
        spriteBatch.draw(Assets.backgroundRegionGame, -Assets.ppuX/2/Assets.PIXELS_TO_METERS, -Assets.ppuY/2/Assets.PIXELS_TO_METERS, Assets.ppuX/Assets.PIXELS_TO_METERS, Assets.ppuY/Assets.PIXELS_TO_METERS);
        spriteBatch.draw(Assets.bomb,Assets.actionBordX, Assets.actionBordY, Assets.tailleActionX, Assets.tailleActionY);
        spriteBatch.draw(Assets.musket,Assets.actionBordX, Assets.actionBordY + Assets.tailleActionY, Assets.tailleActionX, Assets.tailleActionY);
        drawBombs();
        drawBullet();
        drawPlayer();

    }


    public void drawPlayer() {
        for (Player player : world.getPlayers()) {
            spriteBatch.draw(Assets.player, (player.getBody().getPosition().x - Assets.sprite.getWidth() / 2), (player.getBody().getPosition().y - Assets.sprite.getHeight() / 2), Assets.widthPlayer, Assets.heightPlayer);
        }
    }

    public void drawBombs() {
        for (Bomb bomb : world.getBombs()) {
           // spriteBatch.draw(Assets.bombGame, (bomb.getBody().getPosition().x), (bomb.getBody().getPosition().y), Assets.spriteBomb.getWidth(), Assets.spriteBomb.getHeight());
            spriteBatch.draw(Assets.bombGame, (bomb.getBody().getPosition().x - Assets.spriteBomb.getWidth() / 2), (bomb.getBody().getPosition().y - Assets.spriteBomb.getHeight() / 2), Assets.spriteBomb.getWidth(), Assets.spriteBomb.getHeight());
        }
    }

    public void drawBullet() {
        for (Bullet bullet : world.getBullets()) {
            spriteBatch.draw(Assets.bullet, (bullet.getBody().getPosition().x - Assets.spriteBullet.getWidth() / 2), (bullet.getBody().getPosition().y - Assets.spriteBullet.getHeight() / 2), Assets.spriteBullet.getWidth(), Assets.spriteBullet.getHeight());
         //   spriteBatch.draw(Assets.bullet, (bullet.getBody().getPosition().x - Assets.spriteBullet.getWidth() / 2), (bullet.getBody().getPosition().y - Assets.spriteBullet.getHeight() / 2), Assets.spriteBullet.getWidth(), Assets.spriteBullet.getHeight());
        }
    }

}